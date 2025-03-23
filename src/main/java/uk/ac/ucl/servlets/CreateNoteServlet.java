package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@WebServlet("/createNote")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,  // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class CreateNoteServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "data/images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();

        // Set attributes needed for the form
        request.setAttribute("newId", model.getIndex().getNotes().size() + 1);
        request.setAttribute("rootDirectory", model.getRootDirectory());
        request.setAttribute("categories", model.getIndex().getCategories());

        request.getRequestDispatcher("/createNote.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String directoryPath = request.getParameter("directoryPath");

        // Get selected categories
        String[] categoryValues = request.getParameterValues("categories");
        ArrayList<String> categories = new ArrayList<>();
        if (categoryValues != null) {
            Collections.addAll(categories, categoryValues);
        }

        // Handle image uploads
        ArrayList<String> imagePaths = new ArrayList<>();

        // Create upload directory structure if it doesn't exist
        String applicationPath = request.getServletContext().getRealPath("");
        String dataPath = applicationPath + File.separator + "data";
        String uploadPath = dataPath + File.separator + "images";


        // Create images directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIRECTORY);


        // Process uploaded files
        try {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getName().equals("imageFiles") && part.getSize() > 0) {
                    String fileName = getSubmittedFileName(part);
                    if (fileName != null && !fileName.isEmpty()) {
                        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

                        // Generate a unique file name to prevent collisions
                        //String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
                        String filePath = uploadPath + File.separator + fileName;

                        // Save the file
                        try (InputStream input = part.getInputStream()) {
                            Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                        }

                        // Add the relative path to the list
                        imagePaths.add("data/images/" + fileName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the note
        Note note = new Note(id, title, content, imagePaths, categories, directoryPath);

        // Add note to directory
        model.addNoteToDirectory(note, directoryPath);

        // Add note to categories
        for (String categoryName : categories) {
            model.addNoteToCategory(note, categoryName);
        }
        model.addNote(note);
        model.saveNotesToFile();
        model.saveCategoriesToFile();

        // Redirect to the directory where the note was created
        response.sendRedirect("directory?path=" + directoryPath);
    }

    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }
}
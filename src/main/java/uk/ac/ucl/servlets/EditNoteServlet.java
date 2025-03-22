package uk.ac.ucl.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

@WebServlet("/editNote")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class EditNoteServlet extends HttpServlet {
    
    private static final String UPLOAD_DIRECTORY = "data/images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        int noteId = Integer.parseInt(request.getParameter("id"));
        System.out.println("note id:" + noteId);
        Note note = model.getNoteByID(noteId);
        if (note != null) {
            request.setAttribute("note", note);
            request.setAttribute("rootDirectory", model.getRootDirectory());
            request.setAttribute("categories", model.getIndex().getCategories());
            request.getRequestDispatcher("/editNote.jsp").forward(request, response);
        } else {
            response.sendRedirect("/index.html");
        }

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String directoryPath = request.getParameter("directoryPath");

        // Get the existing note
        Note existingNote = model.getNoteByID(id);
        if (existingNote == null) {
            response.sendRedirect("index.html");
            return;
        }
        
        // see if we cant shorten this code
        String[] categoryValues = request.getParameterValues("categories");
        ArrayList<String> categories = new ArrayList<>();
        if (categoryValues != null) {
            categories.addAll(Arrays.asList(categoryValues));
        }
        
        // Get existing image paths that were not removed
        ArrayList<String> imagePaths = new ArrayList<>();
        String existingImagePathsJson = request.getParameter("existingImagePaths");
        if (existingImagePathsJson != null && !existingImagePathsJson.isEmpty()) {
            Gson gson = new Gson();
            List<String> existingImagePaths = gson.fromJson(existingImagePathsJson, new TypeToken<List<String>>() {}.getType());
            imagePaths.addAll(existingImagePaths);
        } else {
            // If no JSON data, try to get individual paths
            String[] existingImages = request.getParameterValues("existingImages");
            if (existingImages != null) {
                for (String path : existingImages) {
                    imagePaths.add(path);
                }
            }
        }
        
        // Handle new image uploads
        // Create upload directory structure if it doesn't exist
        String applicationPath = request.getServletContext().getRealPath("");
        String dataPath = applicationPath + File.separator + "data";
        String uploadPath = dataPath + File.separator + "images";

        // Create images directory if it doesn't exist
        File uploadDir = new File(uploadPath);

        
        // Process uploaded files
        try {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getName().equals("imageFiles") && part.getSize() > 0) {
                    String fileName = getSubmittedFileName(part);
                    if (fileName != null && !fileName.isEmpty()) {
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

       existingNote.setCategories(categories);
       existingNote.setDirectoryPath(directoryPath);
       existingNote.setName(title);
       existingNote.setImagePaths(imagePaths);
       existingNote.setText(content);
       System.out.println("Ganged up " + categories + " " + model.findDirectory(existingNote.getDirectoryPath()).getName() + " " + title + " " +imagePaths + " "+ content);
       model.saveNotesToFile();
        
        // Redirect to view the updated note
        response.sendRedirect("viewNote?id=" + id);
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
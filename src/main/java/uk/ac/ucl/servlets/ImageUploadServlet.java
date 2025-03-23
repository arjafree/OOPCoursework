package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@WebServlet("/uploadImage")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,  // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class ImageUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "data/images";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//        // Create data directory if it doesn't exist
//        File dataDir = new File(dataPath);
//        if (!dataDir.exists()) {
//            dataDir.mkdir();
//        }
//
//        // Create images directory if it doesn't exist
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }

        ArrayList<String> uploadedFilePaths = new ArrayList<>();

        try {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getName().startsWith("imageFile") && part.getSize() > 0) {
                    String fileName = getSubmittedFileName(part);
                    String fileExtension = fileName.substring(fileName.lastIndexOf("."));

                    // Generate a unique file name to prevent collisions
                    String uniqueFileName = UUID.randomUUID() + fileExtension;
                    String filePath = UPLOAD_DIRECTORY + File.separator + uniqueFileName;

                    // Save the file
                    try (InputStream input = part.getInputStream()) {
                        Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                    }

                    // Add the relative path to the list
                    uploadedFilePaths.add("data/images/" + uniqueFileName);
                }
            }

            // Return the list of file paths as JSON
            response.setContentType("application/json");
            response.getWriter().write(new com.google.gson.Gson().toJson(uploadedFilePaths));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error uploading files: " + e.getMessage());
        }
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
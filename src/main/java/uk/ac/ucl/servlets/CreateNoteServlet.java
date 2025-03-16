package uk.ac.ucl.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;

@WebServlet("/createNote")
public class CreateNoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        int id = model.getIndex().getNotes().size()+1;
        request.setAttribute("id", id);
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String directoryPath = request.getParameter("directoryPath");

        // Read categories and imagePaths from request parameters
        String categoriesJson = request.getParameter("categories");
        String imagePathsJson = request.getParameter("imagePaths");

        // Parse JSON strings into ArrayLists
        Gson gson = new Gson();
        ArrayList<String> categories = gson.fromJson(categoriesJson, new TypeToken<List<String>>() {}.getType());
        ArrayList<String> imagePaths = gson.fromJson(imagePathsJson, new TypeToken<List<String>>() {}.getType());

        Note note = new Note(id, title, content, imagePaths, categories);
        for(String categoryName : categories){
            model.addNoteToCategory(note, categoryName);
        }
        model.addNoteToDirectory(note, directoryPath);
        response.sendRedirect("directory?path=" + directoryPath);
    }
}
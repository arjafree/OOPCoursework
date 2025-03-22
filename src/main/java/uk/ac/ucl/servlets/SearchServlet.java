package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        String query = request.getParameter("query");

        if (query == null || query.trim().isEmpty()) {
            response.sendRedirect("index.html");
            return;
        }

        // Perform the search
        ArrayList<Note> searchResults = searchNotes(model, query.trim().toLowerCase());

        // Set attributes for the search results page
        request.setAttribute("query", query);
        request.setAttribute("searchResults", searchResults);
        request.setAttribute("resultCount", searchResults.size());

        // Forward to the search results page
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }

    private ArrayList<Note> searchNotes(Model model, String query) {
        ArrayList<Note> results = new ArrayList<>();
        ArrayList<Note> allNotes = model.getIndex().getNotes();

        for (Note note : allNotes) {
            if(note == null){
                continue;
            }
            // Search in title
            if (note.getName().toLowerCase().contains(query)) {
                results.add(note);
                continue;
            }

            // Search in content
            if (note.getText().toLowerCase().contains(query)) {
                results.add(note);
                continue;
            }

            // Search in image names
            boolean foundInImages = false;
            for (String imagePath : note.getImagePaths()) {
                String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1).toLowerCase();
                if (imageName.contains(query)) {
                    results.add(note);
                    foundInImages = true;
                    break;
                }
            }

            if (foundInImages) {
                continue;
            }
        }

        return results;
    }
}
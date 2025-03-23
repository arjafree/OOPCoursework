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

@WebServlet("/deleteNote")
public class DeleteNoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();

        try {
            int noteId = Integer.parseInt(request.getParameter("id"));
            Note note = model.getNoteByID(noteId);

            if (note != null) {
                String directoryPath = note.getDirectoryPath();
                model.deleteNote(note);

                // Redirect back to the directory
                if (directoryPath.equals("/")) {
                    response.sendRedirect("index.html");
                } else {
                    response.sendRedirect("directory?path=" + directoryPath);
                }
            } else {
                // Note not found, redirect to home
                response.sendRedirect("index.html");
            }
        } catch (NumberFormatException e) {
            // Invalid ID parameter
            response.sendRedirect("index.html");
        }
    }

}
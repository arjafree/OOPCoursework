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

@WebServlet("/viewNote")
public class ViewNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        int noteId = Integer.parseInt(request.getParameter("id"));
        Note note = model.getNoteByID(noteId);

        if (note != null) {
            request.setAttribute("note", note);
            request.getRequestDispatcher("/viewNote.jsp").forward(request, response);
        } else {
            response.sendRedirect("/index.html");
        }
    }
}
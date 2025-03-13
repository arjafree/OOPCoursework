package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Category;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/displayNotes")
public class DisplayNotesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        String categoryName = request.getParameter("category");
        for (Category category : model.getIndex().getCategories()) {
            if (category.getName().equals(categoryName)) {
                request.setAttribute("notes", model.getNotesFromIDs(category.getNoteIDs()));
                request.setAttribute("categoryName", categoryName);
                request.getRequestDispatcher("notes.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("categories.jsp");
    }
}
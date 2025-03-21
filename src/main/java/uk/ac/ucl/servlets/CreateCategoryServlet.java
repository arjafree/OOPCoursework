package uk.ac.ucl.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Category;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

@WebServlet("/createCategory")
public class CreateCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to the createCategory.jsp page
        response.sendRedirect("createCategory.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the model
        Model model = ModelFactory.getModel();

        // Get the category name from the form
        String categoryName = request.getParameter("categoryName");

        // Validate the category name
        if (categoryName == null || categoryName.trim().isEmpty()) {
            // Redirect back to the form with an error message
            response.sendRedirect("createCategory.jsp?error=empty");
            return;
        }

        // Add the new category to the model
        Category newCategory = new Category(categoryName.trim());
        model.addCategory(newCategory);

        // Redirect to the index page or another appropriate page
        response.sendRedirect("index.html");
    }
}
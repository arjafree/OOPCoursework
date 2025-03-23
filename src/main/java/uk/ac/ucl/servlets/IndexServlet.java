package uk.ac.ucl.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

@WebServlet("/index.html")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        request.setAttribute("categories", model.getIndex().getCategories());
        request.setAttribute("listOflistOfnotes", model.getlistNotesFromCategories());
        request.setAttribute("rootDirectory", model.getRootDirectory());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
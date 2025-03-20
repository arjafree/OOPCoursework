package uk.ac.ucl.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Directory;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

@WebServlet("/directory")
public class DirectoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        String path = request.getParameter("path");
        if(path == null || path.isEmpty()){
            path = "/";
        }
        Directory directory = model.findDirectory(path);
        
        if (directory != null) {
            request.setAttribute("directory", directory);
            request.setAttribute("notes", directory.getNotes());
            request.setAttribute("parentDirectory", model.getParentDirectory(directory));
            request.getRequestDispatcher("/directory.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.html");
        }
    }
}
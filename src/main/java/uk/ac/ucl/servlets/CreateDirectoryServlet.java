package uk.ac.ucl.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;


@WebServlet("/createDirectory")
public class CreateDirectoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        request.setAttribute("rootDirectory", model.getRootDirectory());
        request.getRequestDispatcher("/createDirectory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Model model = ModelFactory.getModel();
        String directoryName = request.getParameter("directoryName");
        String parentPath = request.getParameter("parentPath");

        if (directoryName != null && !directoryName.trim().isEmpty()) {
            model.createDirectory(directoryName, parentPath);

            // Redirect to the parent directory or home if it's the root
            if (parentPath != null && !parentPath.equals("/")) {
                response.sendRedirect("directory?path=" + parentPath);
            } else {
                response.sendRedirect("index.html");
            }
        } else {
            response.sendRedirect("createDirectory.jsp?error=empty");
        }
    }
}
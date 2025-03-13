<%@ page import="uk.ac.ucl.model.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Categories</title>
</head>
<body>
  <h1>Categories</h1>
  <ul>
    <%
      ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
      for (Category category : categories) {
    %>
      <li><a href="displayNotes?category=<%= category.getName() %>"><%= category.getName() %></a></li>
    <%
      }
    %>
  </ul>
  <a href="createCategory.jsp">Create New Category</a>
</body>
</html>
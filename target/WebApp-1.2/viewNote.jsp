<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>View Note</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="container">
    <header>
      <h1>Note Details</h1>
    </header>
    <div class="main-content">
      <%
        Note note = (Note) request.getAttribute("note");
        if (note == null) {
          out.println("<p>Note not found.</p>");
        } else {
      %>
      <h2><%= note.getName() %></h2>
      <p><%= note.getText() %></p>
      <h3>Categories:</h3>
      <ul>
        <%
          for (String category : note.getCategories()) {
        %>
          <li><%= category %></li>
        <%
          }
        %>
      </ul>
      <h3>Images:</h3>
      <ul>
        <%
          for (String imagePath : note.getImagePaths()) {
        %>
          <li><img src="<%= imagePath %>" alt="Image for <%= note.getName() %>"></li>
        <%
          }
        %>
      </ul>
      <a href="index.html">Back to Index</a>
      <%
        }
      %>
    </div>
  </div>
</body>
</html>
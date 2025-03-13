<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>View Note</title>
</head>
<body>
  <%
    Note note = (Note) request.getAttribute("note");
    <link rel="stylesheet" type="text/css" href="styles.css">
  %>
  <h1><%= note.getName() %></h1>
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
  <a href="index.jsp">Back to Index</a>
</body>
</html>
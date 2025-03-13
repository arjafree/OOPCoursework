<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Notes in <%= request.getAttribute("categoryName") %></title>
  <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
  <h1>Notes in <%= request.getAttribute("categoryName") %></h1>
  <ul>
    <%
      ArrayList<Note> notes = (ArrayList<Note>) request.getAttribute("notes");
      for (Note note : notes) {
    %>
      <li><a href="viewNote.jsp?title=<%= note.getTitle() %>"><%= note.getTitle() %></a></li>
    <%
      }
    %>
  </ul>
  <a href="createNote.jsp?category=<%= request.getAttribute("categoryName") %>">Create New Note</a>
  <a href="displayCategories">Back to Categories</a>
</body>
</html>
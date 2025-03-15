<%@ page import="uk.ac.ucl.model.Directory, uk.ac.ucl.model.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Directory</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="container">
    <header>
      <h1>Directory</h1>
    </header>
    <div class="main-content">
      <%
        Directory directory = (Directory) request.getAttribute("directory");
      %>
      <h2><%= directory.getName() %></h2>
      <h3>Subdirectories:</h3>
      <ul>
        <%
          for (Directory subdirectory : directory.getSubdirectories()) {
        %>
          <li><a href="directory?path=<%= directory.getName() + "/" + subdirectory.getName() %>"><%= subdirectory.getName() %></a></li>
        <%
          }
        %>
      </ul>
      <h3>Notes:</h3>
      <ul>
        <%
          for (Note note : directory.getNotes()) {
        %>
          <li><a href="viewNote?id=<%= note.getId() %>"><%= note.getName() %></a></li>
        <%
          }
        %>
      </ul>
      <a href="index.html">Back to Index</a>
    </div>
  </div>
</body>
</html>
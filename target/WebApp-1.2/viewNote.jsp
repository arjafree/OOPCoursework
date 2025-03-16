<%@ page import="uk.ac.ucl.model.Note, uk.ac.ucl.model.Model, uk.ac.ucl.model.ModelFactory" %>
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
    <%
      Note note = (Note) request.getAttribute("note");
      if (note != null) {
        // Get the directory path for this note
        String directoryPath = "/";
        try {
          directoryPath = note.getDirectoryPath();
        } catch (Exception e) {
          // If the method doesn't exist yet
        }

        // Get the first category if available
        String firstCategory = note.getCategories().isEmpty() ? "" : note.getCategories().get(0);
    %>
    <header>
      <h1><%= note.getTitle() %></h1>
      <div class="header-actions">
        <a href="<%= directoryPath.equals("/") ? "index.html" : "directory?path=" + directoryPath %>" class="btn secondary">
          <span class="icon">←</span> Back to Directory
        </a>
        <% if (!firstCategory.isEmpty()) { %>
          <a href="displayNotes?category=<%= firstCategory %>" class="btn secondary">
            <span class="icon">🏷️</span> View Category
          </a>
        <% } %>
        <a href="editNote.jsp?id=<%= note.getId() %>" class="btn primary">
          <span class="icon">✏️</span> Edit Note
        </a>
      </div>
    </header>

    <div class="note-location-info">
      <div class="directory-path">
        <span class="path-label">Directory:</span>
        <%
          String[] pathParts = directoryPath.split("/");
          String currentPath = "";
          for (int i = 0; i < pathParts.length; i++) {
            if (pathParts[i].isEmpty()) {
              if (i == 0) {
                // Root directory
                currentPath = "/";
        %>
                <a href="index.html" class="path-part">Root</a>
        <%
                if (pathParts.length > 1) {
        %>
                  <span class="path-separator">/</span>
        <%
                }
                continue;
              } else {
                continue;
              }
            }
            currentPath += "/" + pathParts[i];
            boolean isLast = (i == pathParts.length - 1);
        %>
          <a href="directory?path=<%= currentPath %>" class="path-part <%= isLast ? "current" : "" %>">
            <%= pathParts[i] %>
          </a>
          <% if (!isLast) { %>
            <span class="path-separator">/</span>
          <% } %>
        <%
          }
        %>
      </div>
    </div>

    <div class="main-content">
      <main class="note-detail">
        <div class="note-content">
          <p><%= note.getText().replace("\n", "<br>") %></p>
        </div>

        <div class="note-metadata">
          <div class="metadata-section">
            <h3>Categories</h3>
            <div class="categories-tags">
              <%
                if (!note.getCategories().isEmpty()) {
                  for (String category : note.getCategories()) {
              %>
                <a href="displayNotes?category=<%= category %>" class="category-tag"><%= category %></a>
              <%
                  }
                } else {
              %>
                <p class="no-categories">No categories assigned</p>
              <%
                }
              %>
            </div>
          </div>

          <%
            if (!note.getImagePaths().isEmpty()) {
          %>
          <div class="metadata-section">
            <h3>Images</h3>
            <div class="note-images">
              <%
                for (String imagePath : note.getImagePaths()) {
              %>
                <div class="image-container">
                  <img src="<%=  {
              %>
                <div class="image-container">
                  <img src="<%= imagePath %>" alt="Image for <%= note.getTitle() %>">
                </div>
              <%
                }
              %>
            </div>
          </div>
          <%
            }
          %>
        </div>
      </main>
    </div>
    <%
      } else {
    %>
    <div class="empty-state">
      <p>Note not found.</p>
      <a href="index.html" class="btn primary">Back to Home</a>
    </div>
    <%
      }
    %>
  </div>
</body>
</html>
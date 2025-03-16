<%@ page import="uk.ac.ucl.model.Directory, uk.ac.ucl.model.Note" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Directory Contents</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="container">
    <%
      Directory directory = (Directory) request.getAttribute("directory");
      if (directory != null) {
        String parentPath = "";
        Directory parent = (Directory) request.getAttribute("parentDirectory");
        if (parent != null) {
          parentPath = parent.getPath();
        }
    %>
    <header>
      <h1>
        <span class="folder-icon">📁</span>
        <%= directory.getName() %>
      </h1>
      <div class="header-actions">
        <% if (!parentPath.isEmpty()) { %>
          <a href="directory?path=<%= parentPath %>" class="btn secondary">
            <span class="icon">↑</span> Parent Directory
          </a>
        <% } else { %>
          <a href="index.html" class="btn secondary">
            <span class="icon">←</span> Back to Home
          </a>
        <% } %>
        <a href="createDirectory.jsp?parent=<%= directory.getPath() %>" class="btn secondary">
          <span class="icon">+</span> New Subdirectory
        </a>
        <a href="createNote.jsp?directory=<%= directory.getPath() %>" class="btn primary">
          <span class="icon">+</span> New Note
        </a>
      </div>
    </header>

    <div class="directory-path-nav">
      <span class="path-label">Path:</span>
      <%
        String[] pathParts = directory.getPath().split("/");
        String currentPath = "";
        for (int i = 0; i < pathParts.length; i++) {
          if (pathParts[i].isEmpty()) continue;
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

    <div class="main-content single-column">
      <main class="notes-container">
        <% if (directory.getSubdirectories() != null && !directory.getSubdirectories().isEmpty()) { %>
          <section class="subdirectories-section">
            <h2>Subdirectories</h2>
            <div class="subdirectories-grid">
              <% for (Directory subdir : directory.getSubdirectories()) { %>
                <div class="directory-card" onclick="location.href='directory?path=<%= subdir.getPath() %>'">
                  <div class="directory-card-title">
                    <span class="folder-icon">📁</span>
                    <%= subdir.getName() %>
                  </div>
                  <div class="directory-card-meta">
                    <%
                      int noteCount = 0;
                      noteCount = subdir.getNotes().size();
                    %>
                    <span class="note-count"><%= noteCount %> notes</span>
                  </div>
                </div>
              <% } %>
            </div>
          </section>
        <% } %>

        <section class="notes-section">
          <h2>Notes</h2>
          <div class="notes-grid">
            <%
              List<Note> notes = directory.getNotes();
              if (notes != null && !notes.isEmpty()) {
                for (Note note : notes) {
            %>
              <div class="note-card" onclick="location.href='viewNote?id=<%= note.getId() %>'">
                <div class="note-title">
                  <span class="note-icon">📝</span>
                  <%= note.getName() %>
                </div>
                <div class="note-preview"><%= note.getText().length() > 100 ? note.getText().substring(0, 100) + "..." : note.getText() %></div>
                <div class="note-meta">
                  <div class="note-categories">
                    <%
                      for (String categoryName : note.getCategories()) {
                    %>
                      <span class="category-tag"><%= categoryName %></span>
                    <%
                      }
                    %>
                  </div>
                </div>
              </div>
            <%
                }
              } else {
            %>
              <div class="empty-notes-card">
                <p>No notes in this directory</p>
                <a href="createNote.jsp?directory=<%= directory.getPath() %>" class="btn secondary">
                  <span class="icon">+</span> Add Note
                </a>
              </div>
            <%
              }
            %>
          </div>
        </section>
      </main>
    </div>
    <%
      } else {
    %>
    <div class="empty-state">
      <p>Directory not found.</p>
      <a href="index.html" class="btn primary">Back to Home</a>
    </div>
    <%
      }
    %>
  </div>
</body>
</html>
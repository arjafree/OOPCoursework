<%@ page import="uk.ac.ucl.model.Index, uk.ac.ucl.model.Note, uk.ac.ucl.model.Category, uk.ac.ucl.model.Model" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Notes App</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="container">
    <header>
      <h1>Notes App</h1>
      <div class="header-actions">
        <a href="createCategory.jsp" class="btn primary">
          <span class="icon">+</span> New Category
        </a>
      </div>
    </header>

    <div class="main-content">
      <main class="notes-container">
        <h2>All Categories</h2>

        <div class="categories-grid">
          <%
            ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
            ArrayList<ArrayList<Note>> listOflistOfnotes = (ArrayList<ArrayList<Note>>) request.getAttribute("listOflistOfnotes");
            if (categories != null && !categories.isEmpty()) {
              for (int i = 0; i < categories.size(); i++) {
                Category category = categories.get(i);
                ArrayList<Note> notes = listOflistOfnotes.get(i);
          %>
            <div class="category-card">
              <div class="category-header">
                <h3 class="category-title">
                  <span class="folder-icon">📁</span>
                  <%= category.getName() %>
                </h3>
                <a href="displayNotes?category=<%= category.getName() %>" class="view-all">View All</a>
              </div>
              <div class="notes-list">
                <%
                  if (notes.size() > 0) {
                    // Display up to 3 notes per category on the index page
                    int notesToShow = Math.min(notes.size(), 3);
                    for (int j = 0; j < notesToShow; j++) {
                      Note note = notes.get(j);
                %>
                  <div class="note-item">
                    <a href="viewNote?id=<%= note.getId() %>" class="note-link">
                      <span class="note-icon">📝</span>
                      <%= note.getName() %>
                    </a>
                  </div>
                <%
                    }
                    // If there are more notes than we're showing
                    if (notes.size() > 3) {
                %>
                  <div class="more-notes">
                    + <%= notes.size() - 3 %> more notes
                  </div>
                <%
                    }
                  } else {
                %>
                  <div class="empty-notes">No notes in this category</div>
                <%
                  }
                %>
              </div>
              <div class="category-footer">
                <a href="createNote.jsp?category=<%= category.getName() %>" class="btn secondary small">
                  <span class="icon">+</span> Add Note
                </a>
              </div>
            </div>
          <%
              }
            } else {
          %>
            <div class="empty-state">
              <p>No categories available. Create your first category to get started!</p>
              <a href="createCategory.jsp" class="btn primary">Create Category</a>
            </div>
          <%
            }
          %>
        </div>
      </main>
    </div>
  </div>
</body>
</html>
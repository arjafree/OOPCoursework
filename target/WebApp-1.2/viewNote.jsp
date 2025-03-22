<%@ page import="uk.ac.ucl.model.Note"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.IOException" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>View Note</title>
  <link rel="stylesheet" href="styles.css">
  <style>
    /* Additional styles to fix layout issues */
    .main-content {
      display: block; /* Override grid layout for this page */
    }

    .note-detail {
      display: grid;
      grid-template-columns: 1fr;
      gap: 1.5rem;
      width: 100%;
    }

    @media (min-width: 768px) {
      .note-detail {
        grid-template-columns: 2fr 1fr;
      }
    }

    .note-content {
      width: 100%;
    }

    .note-metadata {
      width: 100%;
    }

    /* Delete button styling */
    .btn.danger {
      background-color: #ef4444;
      color: white;
      border: 1px solid #dc2626;
    }

    .btn.danger:hover {
      background-color: #dc2626;
    }

    /* Action buttons spacing */
    .header-actions {
      display: flex;
      gap: 0.5rem;
      flex-wrap: wrap;
    }

    /* Delete confirmation modal */
    .delete-confirm-modal {
      display: none;
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
      z-index: 1000;
      justify-content: center;
      align-items: center;
    }

    .modal-content {
      background-color: white;
      padding: 2rem;
      border-radius: 0.5rem;
      max-width: 500px;
      width: 90%;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .modal-title {
      font-size: 1.5rem;
      margin-bottom: 1rem;
      color: #dc2626;
    }

    .modal-message {
      margin-bottom: 1.5rem;
      color: #4b5563;
    }

    .modal-actions {
      display: flex;
      justify-content: flex-end;
      gap: 1rem;
    }
  </style>
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
      <h1><%= note.getName() %></h1>
      <div class="header-actions">
        <a href="<%= directoryPath.equals("/") ? "index.html" : "directory?path=" + directoryPath %>" class="btn secondary">
          <span class="icon">←</span> Back to Directory
        </a>
        <% if (!firstCategory.isEmpty()) { %>
          <a href="displayNotes?category=<%= firstCategory %>" class="btn secondary">
            <span class="icon">🏷️</span> View Category
          </a>
        <% } %>
        <a href="editNote?id=<%= note.getId() %>" class="btn primary">
          <span class="icon">✏️</span> Edit Note
        </a>
        <button id="deleteNoteBtn" class="btn danger">
          <span class="icon">🗑️</span> Delete Note
        </button>
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

    <div class="notes-container">
      <div class="note-detail">
        <div class="note-content">
          <h2>Content</h2>
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
                  <img src="<%= imagePath %>" alt="Image for <%= note.getName() %>">
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
      </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div id="deleteConfirmModal" class="delete-confirm-modal">
      <div class="modal-content">
        <h3 class="modal-title">Delete Note</h3>
        <p class="modal-message">
          Are you sure you want to delete "<%= note.getName() %>"? This action cannot be undone.
        </p>
        <div class="modal-actions">
          <button id="cancelDeleteBtn" class="btn secondary">Cancel</button>
          <a href="deleteNote?id=<%= note.getId() %>" class="btn danger">Delete</a>
        </div>
      </div>
    </div>

    <script>
      // Delete confirmation functionality
      const deleteNoteBtn = document.getElementById('deleteNoteBtn');
      const deleteConfirmModal = document.getElementById('deleteConfirmModal');
      const cancelDeleteBtn = document.getElementById('cancelDeleteBtn');

      deleteNoteBtn.addEventListener('click', function() {
        deleteConfirmModal.style.display = 'flex';
      });

      cancelDeleteBtn.addEventListener('click', function() {
        deleteConfirmModal.style.display = 'none';
      });

      // Close modal when clicking outside
      deleteConfirmModal.addEventListener('click', function(event) {
        if (event.target === deleteConfirmModal) {
          deleteConfirmModal.style.display = 'none';
        }
      });
    </script>
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
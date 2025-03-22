<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page import="java.util.ArrayList, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Notes in <%= request.getAttribute("categoryName") %></title>
  <link rel="stylesheet" href="styles.css">
  <style>
    /* Additional styles for notes page */
    .category-banner {
      background-color: #f3f4f6;
      border-radius: 0.5rem;
      padding: 1.5rem;
      margin-bottom: 2rem;
      display: flex;
      align-items: center;
      justify-content: space-between;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }
    
    .category-info {
      display: flex;
      align-items: center;
      gap: 1rem;
    }
    
    .category-icon {
      font-size: 2rem;
      color: #3b82f6;
      background-color: rgba(59, 130, 246, 0.1);
      width: 3.5rem;
      height: 3.5rem;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 0.5rem;
    }
    
    .category-details h2 {
      margin: 0 0 0.5rem 0;
      font-size: 1.5rem;
      color: #1f2937;
    }
    
    .category-details p {
      margin: 0;
      color: #6b7280;
    }
    
    .notes-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 1.5rem;
      margin-bottom: 2rem;
    }
    
    .note-card {
      background-color: white;
      border-radius: 0.5rem;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
      padding: 1.5rem;
      transition: transform 0.2s, box-shadow 0.2s;
      cursor: pointer;
      display: flex;
      flex-direction: column;
      height: 100%;
      border: 1px solid #e5e7eb;
    }
    
    .note-card:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    
    .note-title {
      font-size: 1.25rem;
      font-weight: 600;
      margin-bottom: 0.75rem;
      color: #1f2937;
      display: flex;
      align-items: center;
      gap: 0.5rem;
    }
    
    .note-icon {
      color: #3b82f6;
    }
    
    .note-preview {
      color: #4b5563;
      margin-bottom: 1rem;
      flex-grow: 1;
      overflow: hidden;
      display: -webkit-box;
      -webkit-line-clamp: 4;
      -webkit-box-orient: vertical;
    }
    
    .note-meta {
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
      font-size: 0.875rem;
      color: #6b7280;
      margin-top: auto;
      padding-top: 1rem;
      border-top: 1px solid #f3f4f6;
    }
    
    .directory-path {
      display: flex;
      align-items: center;
      gap: 0.25rem;
    }
    
    .folder-icon {
      color: #f59e0b;
    }
    
    .note-categories {
      display: flex;
      flex-wrap: wrap;
      gap: 0.5rem;
    }
    
    .category-tag {
      background-color: #f3f4f6;
      color: #4b5563;
      padding: 0.25rem 0.5rem;
      border-radius: 9999px;
      font-size: 0.75rem;
      white-space: nowrap;
    }
    
    .empty-state {
      text-align: center;
      padding: 3rem;
      background-color: #f9fafb;
      border-radius: 0.5rem;
      color: #6b7280;
    }
    
    .empty-state p {
      margin-bottom: 1.5rem;
    }
    
    .header-actions {
      display: flex;
      gap: 0.75rem;
    }
    
    @media (max-width: 768px) {
      .category-banner {
        flex-direction: column;
        align-items: flex-start;
        gap: 1rem;
      }
      
      .category-info {
        width: 100%;
      }
      
      .header-actions {
        width: 100%;
        justify-content: flex-end;
      }
      
      .notes-grid {
        grid-template-columns: 1fr;
      }
    }
  </style>
</head>
<body>
  <div class="container">
    <header>
      <h1>Notes by Category</h1>
      <div class="header-actions">
        <a href="index.html" class="btn secondary">
          <span class="icon">←</span> Back to Home
        </a>
        <a href="createNote?category=<%= request.getAttribute("categoryName") %>" class="btn primary">
          <span class="icon">+</span> New Note
        </a>
      </div>
    </header>

    <div class="category-banner">
      <div class="category-info">
        <div class="category-icon">🏷️</div>
        <div class="category-details">
          <h2><%= request.getAttribute("categoryName") %></h2>
          <%
            ArrayList<Note> notes = (ArrayList<Note>) request.getAttribute("notes");
            int noteCount = notes != null ? notes.size() : 0;
          %>
          <p><%= noteCount %> <%= noteCount == 1 ? "note" : "notes" %> in this category</p>
        </div>
      </div>
    </div>

    <main>
      <%
        if (notes != null && !notes.isEmpty()) {
      %>
        <div class="notes-grid">
          <%
            for (Note note : notes) {
          %>
            <div class="note-card" onclick="location.href='viewNote?id=<%= note.getId() %>'">
              <div class="note-title">
                <span class="note-icon">📝</span>
                <%= note.getName() %>
              </div>
              <div class="note-preview">
                <%= note.getText().length() > 150 ? note.getText().substring(0, 150) + "..." : note.getText() %>
              </div>
              <div class="note-meta">
                <div class="directory-path">
                  <span class="folder-icon">📂</span>
                  <%= note.getDirectoryPath() %>
                </div>
                
                <% if (!note.getCategories().isEmpty()) { %>
                  <div class="note-categories">
                    <% for (String category : note.getCategories()) { %>
                      <span class="category-tag"><%= category %></span>
                    <% } %>
                  </div>
                <% } %>
                
                <% if (!note.getImagePaths().isEmpty()) { %>
                  <div class="note-images-info">
                    <span class="image-icon">🖼️</span>
                    <%= note.getImagePaths().size() %> <%= note.getImagePaths().size() == 1 ? "image" : "images" %>
                  </div>
                <% } %>
              </div>
            </div>
          <%
            }
          %>
        </div>
      <%
        } else {
      %>
        <div class="empty-state">
          <p>No notes in this category yet.</p>
          <a href="createNote?category=<%= request.getAttribute("categoryName") %>" class="btn primary">
            <span class="icon">+</span> Create First Note
          </a>
        </div>
      <%
        }
      %>
    </main>
  </div>
</body>
</html>
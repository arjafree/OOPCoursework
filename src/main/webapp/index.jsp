<%@ page import="uk.ac.ucl.model.Index, uk.ac.ucl.model.Note, uk.ac.ucl.model.Category, uk.ac.ucl.model.Directory" %>
<%@ page import="java.util.ArrayList, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.IOException" %>
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
        <a href="createDirectory.jsp" class="btn primary">
          <span class="icon">+</span> New Directory
        </a>
        <a href="createCategory.jsp" class="btn secondary">
          <span class="icon">+</span> New Category
        </a>
        <a href="createNote.jsp" class="btn secondary">
          <span class="icon">+</span> New Note
        </a>
      </div>
    </header>

    <div class="main-content">
      <aside class="sidebar">
        <div class="directory-tree">
          <h2>Directories</h2>
          <ul class="tree-view">
            <%
              Directory rootDirectory = (Directory) request.getAttribute("rootDirectory");
              if (rootDirectory != null) {
                for (Directory subdirectory : rootDirectory.getSubdirectories()){
                    renderDirectoryTree(out, subdirectory, "");
                }
                if(rootDirectory.getSubdirectories()==null){
              %>
                  <li class="tree-item empty">No directories available</li>
              <%
                }
              } else {
            %>
              <li class="tree-item empty">No directories available</li>
            <%
              }
            %>
          </ul>
        </div>

        <div class="categories-section">
          <h2>Categories</h2>
          <ul class="category-list">
            <%
              ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
              if (categories != null && !categories.isEmpty()) {
                for (Category category : categories) {
            %>
              <li class="category-item">
                <a href="displayNotes?category=<%= category.getName() %>">
                  <span class="tag-icon">🏷️</span>
                  <%= category.getName() %>
                  <span class="note-count"><%= category.getNoteIDs().size() %></span>
                </a>
              </li>
            <%
                }
              } else {
            %>
              <li class="category-item empty">No categories available</li>
            <%
              }
            %>
          </ul>
        </div>
      </aside>

      <main class="notes-container">
        <div class="tabs">
          <div class="tab-header">
            <button class="tab-btn active" data-tab="directories">By Directory</button>
            <button class="tab-btn" data-tab="categories">By Category</button>
          </div>

          <!-- Directory View Tab -->
          <div class="tab-content active" id="directories">
            <h2>Files by Directory</h2>

            <%
              if (rootDirectory != null && rootDirectory.getSubdirectories().size() > 0) {
                for (Directory dir : rootDirectory.getSubdirectories()) {
            %>
              <div class="directory-section">
                <div class="directory-header">
                  <h3 class="directory-title">
                    <span class="folder-icon">📁</span>
                    <%= dir.getName() %>
                  </h3>
                  <a href="directory?path=<%= dir.getPath() %>" class="view-all">View Contents</a>
                </div>

                <div class="notes-grid">
                  <%
                    List<Note> dirNotes = dir.getNotes();
                    if (dirNotes != null && !dirNotes.isEmpty()) {
                      // Display up to 3 notes per directory on the index page
                      int notesToShow = Math.min(dirNotes.size(), 3);
                      for (int i = 0; i < notesToShow; i++) {
                        Note note = dirNotes.get(i);
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
                      // If there are more notes than we're showing
                      if (dirNotes.size() > 3) {
                  %>
                    <div class="more-notes-card">
                      <a href="directory?path=<%= dir.getPath() %>" class="more-notes-link">
                        + <%= dirNotes.size() - 3 %> more notes
                      </a>
                    </div>
                  <%
                      }
                    } else {
                  %>
                    <div class="empty-notes-card">
                      <p>No notes in this directory</p>
                      <a href="createNote.jsp?directory=<%= dir.getPath() %>" class="btn secondary small">
                        <span class="icon">+</span> Add Note
                      </a>
                    </div>
                  <%
                    }
                  %>
                </div>
              </div>
            <%
                }
              } else {
            %>
              <div class="empty-state">
                <p>No directories available. Create your first directory to get started!</p>
                <a href="createDirectory.jsp" class="btn primary">Create Directory</a>
              </div>
            <%
              }
            %>
          </div>

          <!-- Category View Tab -->
          <div class="tab-content" id="categories">
            <h2>Notes by Category</h2>

            <%
              if (categories != null && !categories.isEmpty()) {
                List<List<Note>> listOflistOfnotes = (List<List<Note>>) request.getAttribute("listOflistOfnotes");
                for (int i = 0; i < categories.size(); i++) {
                  Category category = categories.get(i);
                  List<Note> categoryNotes = listOflistOfnotes.get(i);
            %>
              <div class="category-section">
                <div class="category-header">
                  <h3 class="category-title">
                    <span class="tag-icon">🏷️</span>
                    <%= category.getName() %>
                  </h3>
                  <a href="displayNotes?category=<%= category.getName() %>" class="view-all">View All</a>
                </div>

                <div class="notes-grid">
                  <%
                    if (categoryNotes != null && !categoryNotes.isEmpty()) {
                      // Display up to 3 notes per category on the index page
                      int notesToShow = Math.min(categoryNotes.size(), 3);
                      for (int j = 0; j < notesToShow; j++) {
                        Note note = categoryNotes.get(j);
                  %>
                    <div class="note-card" onclick="location.href='viewNote?id=<%= note.getId() %>'">
                      <div class="note-title">
                        <span class="note-icon">📝</span>
                        <%= note.getName() %>
                      </div>
                      <div class="note-preview"><%= note.getText().length() > 100 ? note.getText().substring(0, 100) + "..." : note.getText() %></div>
                      <div class="note-meta">
                        <div class="note-directory">
                          <span class="directory-path">📂
                            <%
                              String dirPath = note.getDirectoryPath();
                            %>
                            <%= dirPath %>
                          </span>
                        </div>
                      </div>
                    </div>
                  <%
                      }
                      // If there are more notes than we're showing
                      if (categoryNotes.size() > 3) {
                  %>
                    <div class="more-notes-card">
                      <a href="displayNotes?category=<%= category.getName() %>" class="more-notes-link">
                        + <%= categoryNotes.size() - 3 %> more notes
                      </a>
                    </div>
                  <%
                      }
                    } else {
                  %>
                    <div class="empty-notes-card">
                      <p>No notes in this category</p>
                      <a href="createNote.jsp?category=<%= category.getName() %>" class="btn secondary small">
                        <span class="icon">+</span> Add Note
                      </a>
                    </div>
                  <%
                    }
                  %>
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
        </div>
      </main>
    </div>
  </div>

  <script>
    // Tab switching functionality
    document.querySelectorAll('.tab-btn').forEach(button => {
      button.addEventListener('click', () => {
        // Remove active class from all buttons and content
        document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));

        // Add active class to clicked button
        button.classList.add('active');

        // Show corresponding content
        const tabId = button.getAttribute('data-tab');
        document.getElementById(tabId).classList.add('active');
      });
    });

    // Toggle directory tree items
    document.querySelectorAll('.toggle-icon').forEach(icon => {
      icon.addEventListener('click', function(e) {
        e.preventDefault();
        e.stopPropagation();
        const treeItem = this.closest('.tree-item');
        treeItem.classList.toggle('expanded');
        this.textContent = treeItem.classList.contains('expanded') ? '▼' : '▶';
      });
    });
  </script>
</body>
</html>

<%!
  // Helper method to recursively render directory tree
  private void renderDirectoryTree(JspWriter out, Directory directory, String indent) throws IOException {
    String dirPath = directory.getPath();
    String dirName = directory.getName();
    List<Directory> subdirectories = directory.getSubdirectories();
    boolean hasSubdirs = subdirectories != null && !subdirectories.isEmpty();

    out.println("<li class=\"tree-item" + (hasSubdirs ? " has-children" : "") + "\">");
    out.println("  <div class=\"tree-item-content\">");
    if (hasSubdirs) {
      out.println("    <span class=\"toggle-icon\">▶</span>");
    } else {
      out.println("    <span class=\"toggle-icon empty\"></span>");
    }
    out.println("    <span class=\"folder-icon\">📁</span>");
    out.println("    <a href=\"directory?path=" + dirPath + "\">" + dirName + "</a>");
    out.println("  </div>");

    if (hasSubdirs) {
      out.println("  <ul class=\"subtree\">");
      for (Directory subdir : subdirectories) {
        renderDirectoryTree(out, subdir, indent + "  ");
      }
      out.println("  </ul>");
    }

    out.println("</li>");
  }
%>
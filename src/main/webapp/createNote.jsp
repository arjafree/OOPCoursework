<%@ page import="uk.ac.ucl.model.Category, uk.ac.ucl.model.Directory" %>
<%@ page import="java.util.ArrayList, java.util.List" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Note</title>
  <link rel="stylesheet" href="styles.css">
  <style>
    /* Additional styles for file upload */
    .file-upload-container {
      border: 2px dashed #d1d5db;
      border-radius: 0.375rem;
      padding: 1.5rem;
      text-align: center;
      margin-bottom: 1rem;
    }
    
    .upload-icon {
      font-size: 2rem;
      margin-bottom: 0.5rem;
      color: #6b7280;
    }
    
    .upload-text {
      color: #6b7280;
      margin-bottom: 0.5rem;
    }
    
    .upload-hint {
      font-size: 0.875rem;
      color: #9ca3af;
      margin-bottom: 1rem;
    }
    
    .file-input-container {
      display: flex;
      justify-content: center;
      margin-top: 1rem;
    }
  </style>
</head>
<body>
  <div class="container">
    <header>
      <h1>Create New Note</h1>
      <div class="header-actions">
        <a href="javascript:history.back()" class="btn secondary">
          <span class="icon">←</span> Cancel
        </a>
      </div>
    </header>

    <div class="main-content single-column">
      <main class="notes-container">
        <div class="note-editor">
          <form action="createNote" method="POST" enctype="multipart/form-data">
            <%
              int newId = 0;
              try {
                newId = (int) request.getAttribute("newId");
              } catch (Exception e) {
                // If attribute is not set, use a default value
              }

              // Get the directory and category from the request parameters
              String directoryParam = request.getParameter("directory");
              String categoryParam = request.getParameter("category");

              // If no directory is specified, use the root directory
              if (directoryParam == null || directoryParam.isEmpty()) {
                directoryParam = "/";
              }
            %>
            <input type="hidden" name="id" value="<%= newId %>">
            <!-- <input type="hidden" name="directoryPath" value="<%= directoryParam %>"> -->

            <div class="form-group">
              <label for="title">Title</label>
              <input type="text" id="title" name="title" required>
            </div>

            <div class="form-group">
              <label for="content">Content</label>
              <textarea id="content" name="content" rows="8" required></textarea>
            </div>

            <div class="form-group">
              <label for="directorySelect">Directory</label>
              <select id="directorySelect" name="directoryPath">
                <%
                  // Get the root directory
                  Directory rootDirectory = (Directory) request.getAttribute("rootDirectory");
                  if (rootDirectory != null) {
                    renderDirectoryOptions(out, rootDirectory, "", directoryParam);
                  }
                %>
              </select>
            </div>

            <div class="form-group">
              <label>Categories</label>
              <div class="category-checkboxes">
                <%
                  ArrayList<Category> categories = (ArrayList) request.getAttribute("categories");
                  if (categories != null) {
                    for (Category category : categories) {
                      boolean isSelected = category.getName().equals(categoryParam);
                %>
                <div class="checkbox-item">
                  <input type="checkbox"
                         id="category-<%= category.getName() %>"
                         name="categories"
                         value="<%= category.getName() %>"
                         <%= isSelected ? "checked" : "" %>>
                  <label for="category-<%= category.getName() %>"><%= category.getName() %></label>
                </div>
                <%
                    }
                  }
                %>
              </div>
            </div>

            <div class="form-group">
              <label>Images</label>
              <div class="file-upload-container">
                <div class="upload-icon">📷</div>
                <div class="upload-text">Upload images for your note</div>
                <div class="upload-hint">Supported formats: JPG, PNG, GIF</div>
                <div class="file-input-container">
                  <input type="file" name="imageFiles" multiple accept="image/*">
                </div>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn secondary" onclick="history.back()">Cancel</button>
              <button type="submit" class="btn primary">Save Note</button>
            </div>
          </form>
        </div>
      </main>
    </div>
  </div>
</body>
</html>

<%!
  // Helper method to recursively render directory options
  private void renderDirectoryOptions(JspWriter out, Directory directory, String indent, String selectedPath) throws IOException {
    String dirPath = directory.getPath();
    String dirName = directory.getName();
    boolean isSelected = dirPath.equals(selectedPath);

    // Root directory needs special handling
    if (dirPath.equals("/")) {
      out.println("<option value=\"" + dirPath + "\"" + (isSelected ? " selected" : "") + ">" +
                  indent + "Root" + "</option>");
    } else {
      out.println("<option value=\"" + dirPath + "\"" + (isSelected ? " selected" : "") + ">" +
                  indent + dirName + "</option>");
    }

    List<Directory> subdirectories = directory.getSubdirectories();
    if (subdirectories != null && !subdirectories.isEmpty()) {
      for (Directory subdir : subdirectories) {
        renderDirectoryOptions(out, subdir, indent + "— ", selectedPath);
      }
    }
  }
%>
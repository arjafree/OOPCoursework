<%@ page import="uk.ac.ucl.model.Category, uk.ac.ucl.model.Directory, uk.ac.ucl.model.Note"%>
<%@ page import="java.util.ArrayList, java.util.List" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Note</title>
  <link rel="stylesheet" href="styles.css">
  <style>
    /* Additional styles for file upload */
    .file-upload-container {
      border: 2px dashed #d1d5db;
      border-radius: 0.375rem;
      padding: 1.5rem;
      text-align: center;
      margin-bottom: 1rem;
      transition: background-color 0.2s, border-color 0.2s;
      position: relative;
    }
    
    .file-upload-container.drag-over {
      background-color: rgba(59, 130, 246, 0.05);
      border-color: #3b82f6;
    }
    
    .file-upload-container input[type="file"] {
      position: absolute;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      opacity: 0;
      cursor: pointer;
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
    }
    
    .image-preview-container {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
      gap: 0.75rem;
      margin-top: 1rem;
    }
    
    .image-preview-item {
      position: relative;
      border-radius: 0.375rem;
      overflow: hidden;
      border: 1px solid #e5e7eb;
    }
    
    .image-preview-item img {
      width: 100%;
      height: 150px;
      object-fit: cover;
      display: block;
    }
    
    .image-preview-item .remove-image {
      position: absolute;
      top: 0.25rem;
      right: 0.25rem;
      background-color: rgba(0, 0, 0, 0.5);
      color: white;
      border-radius: 9999px;
      width: 1.5rem;
      height: 1.5rem;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      font-size: 0.75rem;
    }
    
    .image-preview-item .image-name {
      font-size: 0.75rem;
      padding: 0.25rem 0.5rem;
      background-color: rgba(0, 0, 0, 0.5);
      color: white;
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    
    .existing-images {
      margin-bottom: 1.5rem;
    }
    
    .existing-images h4 {
      margin-bottom: 0.75rem;
      font-size: 1rem;
      color: #4b5563;
    }
    
    .no-js-file-input {
      margin-top: 1rem;
      display: none;
    }

  </style>
</head>
<body>
  <div class="container">
    <%

      // Get the note from the servlet
      Note note = (Note) request.getAttribute("note");
      if (note != null) {
        String directoryPath = note.getDirectoryPath();
    %>
    <header>
      <h1>Edit Note</h1>
      <div class="header-actions">
        <a href="viewNote?id=<%= note.getId() %>" class="btn secondary">
          <span class="icon">←</span> Cancel
        </a>
      </div>
    </header>

    <div class="main-content single-column">
      <main class="notes-container">
        <div class="note-editor">
          <form action="editNote" method="POST" id="editNoteForm" enctype="multipart/form-data">
            <input type="hidden" name="id" value="<%= note.getId() %>">
            <input type="hidden" id="directoryPath" name="directoryPath" value="<%= directoryPath %>">

            <div class="form-group">
              <label for="title">Title</label>
              <input type="text" id="title" name="title" value="<%= note.getName() %>" required>
            </div>

            <div class="form-group">
              <label for="content">Content</label>
              <textarea id="content" name="content" rows="8" required><%= note.getText() %></textarea>
            </div>

            <div class="form-group">
              <label for="directorySelect">Directory</label>
              <select id="directorySelect" name="directorySelect" onchange="updateDirectoryPath()">
                <%
                  // Get the root directory
                  Directory rootDirectory = (Directory) request.getAttribute("rootDirectory");
                  if (rootDirectory != null) {
                    renderDirectoryOptions(out, rootDirectory, "", directoryPath);
                  }
                %>
              </select>
            </div>

            <div class="form-group">
              <label>Categories</label>
              <div class="category-checkboxes">
                <%
                  ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
                  if (categories != null) {
                    for (Category category : categories) {
                      boolean isSelected = note.getCategories().contains(category.getName());
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
              
              <% if (!note.getImagePaths().isEmpty()) { %>
              <div class="existing-images">
                <h4>Current Images</h4>
                <div class="image-preview-container" id="existingImagesContainer">
                  <% for (String imagePath : note.getImagePaths()) { %>
                    <div class="image-preview-item">
                      <img src="<%= imagePath %>" alt="Image">
                      <div class="remove-image" data-path="<%= imagePath %>">✕</div>
                      <div class="image-name"><%= imagePath.substring(imagePath.lastIndexOf("/") + 1) %></div>
                      <input type="hidden" name="existingImages" value="<%= imagePath %>" class="existing-image-path">
                    </div>
                  <% } %>
                </div>
              </div>
              <% } %>
              
              <div class="file-upload-container js-only" id="dropZone">
                <input type="file" id="imageUpload" name="imageFiles" multiple accept="image/*">
                <div class="upload-icon">📷</div>
                <div class="upload-text">Drag and drop new images here or click to browse</div>
                <div class="upload-hint">Supported formats: JPG, PNG, GIF</div>
              </div>
              <div class="image-preview-container js-only" id="imagePreviewContainer"></div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn secondary" onclick="location.href='viewNote?id=<%= note.getId() %>'">Cancel</button>
              <button type="button" class="btn primary js-only" id="submitButton">Save Changes</button>
            </div>
          </form>
        </div>
      </main>
    </div>
    
    <% } else { %>
    <div class="empty-state">
      <p>Note not found.</p>
      <a href="index.html" class="btn primary">Back to Home</a>
    </div>
    <% } %>
  </div>

  <script>

    // Directory path update
    function updateDirectoryPath() {
      const directorySelect = document.getElementById('directorySelect');
      const directoryPath = document.getElementById('directoryPath');
      directoryPath.value = directorySelect.value;
    }
    
    // File upload and drag & drop functionality
    const dropZone = document.getElementById('dropZone');
    const fileInput = document.getElementById('imageUpload');
    const previewContainer = document.getElementById('imagePreviewContainer');
    const submitButton = document.getElementById('submitButton');
    let uploadedFiles = [];
    
    // Handle existing images
    const existingImagesContainer = document.getElementById('existingImagesContainer');
    if (existingImagesContainer) {
      const removeButtons = existingImagesContainer.querySelectorAll('.remove-image');
      removeButtons.forEach(button => {
        button.addEventListener('click', function() {
          const imagePath = this.getAttribute('data-path');
          const imageItem = this.closest('.image-preview-item');
          imageItem.remove();
        });
      });
    }
    
    // Handle drag events
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
      dropZone.addEventListener(eventName, preventDefaults, false);
    });
    
    function preventDefaults(e) {
      e.preventDefault();
      e.stopPropagation();
    }
    
    // Highlight drop zone when dragging over it
    ['dragenter', 'dragover'].forEach(eventName => {
      dropZone.addEventListener(eventName, highlight, false);
    });
    
    ['dragleave', 'drop'].forEach(eventName => {
      dropZone.addEventListener(eventName, unhighlight, false);
    });
    
    function highlight() {
      dropZone.classList.add('drag-over');
    }
    
    function unhighlight() {
      dropZone.classList.remove('drag-over');
    }
    
    // Handle dropped files
    dropZone.addEventListener('drop', handleDrop, false);
    
    function handleDrop(e) {
      const dt = e.dataTransfer;
      const files = dt.files;
      handleFiles(files);
    }
    
    // Handle selected files from file input
    fileInput.addEventListener('change', function() {
      handleFiles(this.files);
    });
    
    function handleFiles(files) {
      if (files.length > 0) {
        Array.from(files).forEach(file => {
          if (file.type.startsWith('image/')) {
            uploadedFiles.push(file);
            previewFile(file);
          }
        });
      }
    }
    
    function previewFile(file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = function() {
        const preview = document.createElement('div');
        preview.className = 'image-preview-item';
        preview.innerHTML = `
          <img src="${reader.result}" alt="${file.name}">
          <div class="remove-image" data-filename="${file.name}">✕</div>
          <div class="image-name">${file.name}</div>
        `;
        previewContainer.appendChild(preview);
        
        // Add event listener to remove button
        preview.querySelector('.remove-image').addEventListener('click', function() {
          const filename = this.getAttribute('data-filename');
          uploadedFiles = uploadedFiles.filter(f => f.name !== filename);
          preview.remove();
        });
      }
    }
    
    // Form submission
    submitButton.addEventListener('click', function() {
      const form = document.getElementById('editNoteForm');
      
      // Create FormData object
      const formData = new FormData(form);
      
      // Add each file to FormData
      uploadedFiles.forEach((file, index) => {
        formData.append(`imageFile${index}`, file);
      });
      
      // Add number of files
      formData.append('imageCount', uploadedFiles.length);
      
      // Get remaining existing images
      const existingImagePaths = [];
      document.querySelectorAll('.existing-image-path').forEach(input => {
        existingImagePaths.push(input.value);
      });
      formData.append('existingImagePaths', JSON.stringify(existingImagePaths));
      
      // Submit the form
      fetch('editNote', {
        method: 'POST',
        body: formData
      })
      .then(response => {
        if (response.ok) {
          // Redirect to view the note
          window.location.href = `viewNote?id=${note.getId()}`;
        } else {
          alert('Error saving note. Please try again.');
        }
      })
      .catch(error => {
        console.error('Error:', error);
        alert('Error saving note. Please try again.');
      });
    });
  </script>
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
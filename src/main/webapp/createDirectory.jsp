<%@ page import="uk.ac.ucl.model.Directory" %>
<%@ page import="uk.ac.ucl.model.Model" %>
<%@ page import="uk.ac.ucl.model.ModelFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Directory</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="container">
    <header>
      <h1>Create New Directory</h1>
      <div class="header-actions">
        <a href="javascript:history.back()" class="btn secondary">
          <span class="icon">←</span> Cancel
        </a>
      </div>
    </header>

    <div class="main-content single-column">
      <main class="notes-container">
        <div class="note-editor">
          <form action="createDirectory" method="POST">
            <input type="hidden" id="parentPath" name="parentPath" value="/">

            <div class="form-group">
              <label for="directoryName">Directory Name</label>
              <input type="text" id="directoryName" name="directoryName" required>
            </div>

            <div class="form-group">
              <label for="parentSelect">Parent Directory</label>
              <select id="parentSelect" name="parentPath" onchange="updateParentPath()">
                <%
                  Directory rootDirectory = (Directory) request.getAttribute("rootDirectory");
                  if (rootDirectory != null) {

                    renderDirectoryOptions(out, rootDirectory, "", "/");
                  }
                %>
              </select>
            </div>

            <div class="form-actions">
              <button type="button" class="btn secondary" onclick="history.back()">Cancel</button>
              <button type="submit" class="btn primary">Create Directory</button>
            </div>
          </form>
        </div>
      </main>
    </div>
  </div>

  <script>
    function updateParentPath() {
      const parentSelect = document.getElementById('parentSelect');
      const parentPath = document.getElementById('parentPath');
      parentPath.value = parentSelect.value;
    }
  </script>
</body>
</html>

<%!
  // Helper method to recursively render directory options
  private void renderDirectoryOptions(JspWriter out, Directory directory, String indent, String selectedPath) throws IOException {
    String dirPath = directory.getPath();
    String dirName = directory.getName();
    boolean isSelected = dirPath.equals(selectedPath);

    // Render the current directory as an option
    out.println("<option value=\"" + dirPath + "\"" + (isSelected ? " selected" : "") + ">" +
                indent + dirName + "</option>");

    // Recursively render subdirectories
    List<Directory> subdirectories = directory.getSubdirectories();
    if (subdirectories != null && !subdirectories.isEmpty()) {
      for (Directory subdir : subdirectories) {
        renderDirectoryOptions(out, subdir, indent + "— ", selectedPath);
      }
    }
  }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Category</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="container">
    <header>
      <h1>Create New Category</h1>
      <div class="header-actions">
        <a href="javascript:history.back()" class="btn secondary">
          <span class="icon">←</span> Cancel
        </a>
      </div>
    </header>

    <div class="main-content">
      <main class="notes-container">
        <div class="note-editor">
          <form action="createCategory" method="POST">
            <div class="form-group">
              <label for="categoryName">Category Name</label>
              <input type="text" id="categoryName" name="categoryName" required>
            </div>

            <div class="form-actions">
              <button type="button" class="btn secondary" onclick="history.back()">Cancel</button>
              <button type="submit" class="btn primary">Create Category</button>
            </div>
          </form>
        </div>
      </main>
    </div>
  </div>
</body>
</html>
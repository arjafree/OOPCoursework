<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Category</title>
  <link rel="stylesheet" href="styles.css">
  <style>
    .main-content.single-column {
      display: block;
      max-width: 600px;
      margin: 0 auto;
    }
    
    .category-form-container {
      background-color: white;
      border-radius: 0.5rem;
      padding: 2rem;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }
    
    .category-icon {
      font-size: 2.5rem;
      color: #3b82f6;
      margin-bottom: 1rem;
      text-align: center;
      display: block;
    }
    
    .form-description {
      color: #6b7280;
      margin-bottom: 1.5rem;
      text-align: center;
      font-size: 0.95rem;
    }
    
    .form-group {
      margin-bottom: 1.5rem;
    }
    
    .form-group label {
      font-size: 1.1rem;
      margin-bottom: 0.75rem;
    }
    
    .form-group input[type="text"] {
      padding: 0.875rem;
      font-size: 1.1rem;
      border: 1px solid #d1d5db;
      border-radius: 0.375rem;
      width: 100%;
      transition: border-color 0.2s, box-shadow 0.2s;
    }
    
    .form-group input[type="text"]:focus {
      border-color: #3b82f6;
      box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
      outline: none;
    }
    
    .form-hint {
      font-size: 0.875rem;
      color: #6b7280;
      margin-top: 0.5rem;
    }
    
    .form-actions {
      display: flex;
      justify-content: flex-end;
      gap: 1rem;
      margin-top: 2rem;
    }
    
    .btn {
      padding: 0.75rem 1.5rem;
      font-size: 1rem;
    }
  </style>
</head>
<body>
  <div class="container">
    <header>
      <h1>Create New Category</h1>
      <div class="header-actions">
        <a href="javascript:history.back()" class="btn secondary">
          <span class="icon">←</span> Back
        </a>
      </div>
    </header>

    <div class="main-content single-column">
      <div class="category-form-container">
        <span class="category-icon">🏷️</span>
        <h2 style="text-align: center; margin-bottom: 1rem;">Add a New Category</h2>
        <p class="form-description">
          Categories help you organize your notes by topic or theme. 
          You can assign multiple categories to each note.
        </p>
        
        <form action="createCategory" method="POST">
          <div class="form-group">
            <label for="categoryName">Category Name</label>
            <input type="text" id="categoryName" name="categoryName" required 
                   placeholder="Enter a name for your category" autofocus>
            <p class="form-hint">Choose a descriptive name that will help you identify your notes.</p>
          </div>

          <div class="form-actions">
            <button type="button" class="btn secondary" onclick="history.back()">Cancel</button>
            <button type="submit" class="btn primary">Create Category</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>
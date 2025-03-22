<%@ page import="java.util.ArrayList" %>
<%@ page import="uk.ac.ucl.model.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Search Results - Notes App</title>
  <link rel="stylesheet" href="styles.css">
  <style>
    /* Search results specific styles */
    .search-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1.5rem;
    }

    .search-info {
      color: #6b7280;
    }

    .search-query {
      font-weight: bold;
      color: #3b82f6;
    }

    .search-container {
      margin-bottom: 2rem;
      width: 100%;
      max-width: 600px;
    }

    .search-form {
      display: flex;
      width: 100%;
    }

    .search-input {
      flex: 1;
      padding: 0.75rem 1rem;
      border: 1px solid #d1d5db;
      border-right: none;
      border-radius: 0.375rem 0 0 0.375rem;
      font-size: 1rem;
    }

    .search-input:focus {
      outline: none;
      border-color: #3b82f6;
      box-shadow: 0 0 0 1px rgba(59, 130, 246, 0.5);
    }

    .search-button {
      background-color: #3b82f6;
      color: white;
      border: none;
      border-radius: 0 0.375rem 0.375rem 0;
      padding: 0 1rem;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .search-button:hover {
      background-color: #2563eb;
    }

    .search-icon {
      font-size: 1.25rem;
    }

    .highlight {
      background-color: rgba(59, 130, 246, 0.2);
      padding: 0.125rem 0.25rem;
      border-radius: 0.25rem;
    }

    .match-info {
      font-size: 0.875rem;
      color: #6b7280;
      margin-top: 0.5rem;
    }

    .match-type {
      font-weight: bold;
      color: #4b5563;
    }
  </style>
</head>
<body>
  <div class="container">
    <header>
      <div class="header-top">
        <h1>Search Results</h1>

        <!-- Search Bar -->
        <div class="search-container">
          <form action="search" method="GET" class="search-form">
            <input type="text" name="query" value="<%= request.getAttribute("query") %>" placeholder="Search notes..." class="search-input" required>
            <button type="submit" class="search-button">
              <span class="search-icon">🔍</span>
            </button>
          </form>
        </div>
      </div>

      <div class="header-actions">
        <a href="index.html" class="btn secondary">
          <span class="icon">←</span> Back to Home
        </a>
      </div>
    </header>

    <div class="search-header">
      <p class="search-info">
        Found <strong><%= request.getAttribute("resultCount") %></strong> results for
        "<span class="search-query"><%= request.getAttribute("query") %></span>"
      </p>
    </div>

    <div class="notes-container">
      <%
        ArrayList<Note> searchResults = (ArrayList<Note>) request.getAttribute("searchResults");
        String query = ((String) request.getAttribute("query")).toLowerCase();

        if (searchResults != null && !searchResults.isEmpty()) {
          for (Note note : searchResults) {
            // Determine where the match was found
            boolean matchInTitle = note.getName().toLowerCase().contains(query);
            boolean matchInContent = note.getText().toLowerCase().contains(query);
            boolean matchInImage = false;
            String matchedImageName = "";

            for (String imagePath : note.getImagePaths()) {
              String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1).toLowerCase();
              if (imageName.contains(query)) {
                matchInImage = true;
                matchedImageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                break;
              }
            }
      %>
        <div class="note-card" onclick="location.href='viewNote?id=<%= note.getId() %>'">
          <div class="note-title">
            <span class="note-icon">📝</span>
            <% if (matchInTitle) {
                 // Highlight the matching part in the title
                 String title = note.getName();
                 int startIndex = title.toLowerCase().indexOf(query);
                 String before = title.substring(0, startIndex);
                 String match = title.substring(startIndex, startIndex + query.length());
                 String after = title.substring(startIndex + query.length());
            %>
              <%= before %><span class="highlight"><%= match %></span><%= after %>
            <% } else { %>
              <%= note.getName() %>
            <% } %>
          </div>

          <div class="note-preview">
            <% if (matchInContent) {
                 // Show a snippet of content around the match
                 String content = note.getText();
                 int startIndex = content.toLowerCase().indexOf(query);
                 int snippetStart = Math.max(0, startIndex - 50);
                 int snippetEnd = Math.min(content.length(), startIndex + query.length() + 50);

                 // Adjust snippet to not cut words
                 if (snippetStart > 0) {
                   while (snippetStart > 0 && content.charAt(snippetStart) != ' ') {
                     snippetStart--;
                   }
                 }

                 if (snippetEnd < content.length()) {
                   while (snippetEnd < content.length() && content.charAt(snippetEnd) != ' ') {
                     snippetEnd++;
                   }
                 }

                 String snippet = content.substring(snippetStart, snippetEnd);

                 // Add ellipsis if needed
                 if (snippetStart > 0) {
                   snippet = "..." + snippet;
                 }

                 if (snippetEnd < content.length()) {
                   snippet = snippet + "...";
                 }

                 // Highlight the match in the snippet
                 int matchStartInSnippet = snippet.toLowerCase().indexOf(query);
                 String beforeMatch = snippet.substring(0, matchStartInSnippet);
                 String match = snippet.substring(matchStartInSnippet, matchStartInSnippet + query.length());
                 String afterMatch = snippet.substring(matchStartInSnippet + query.length());
            %>
              <%= beforeMatch %><span class="highlight"><%= match %></span><%= afterMatch %>
            <% } else { %>
              <%= note.getText().length() > 150 ? note.getText().substring(0, 150) + "..." : note.getText() %>
            <% } %>
          </div>

          <div class="note-meta">
            <div class="directory-path">
              <span class="folder-icon">📂</span> <%= note.getDirectoryPath() %>
            </div>

            <div class="match-info">
              <% if (matchInTitle) { %>
                <span class="match-type">Match in title</span>
              <% } %>

              <% if (matchInContent) { %>
                <% if (matchInTitle) { %> • <% } %>
                <span class="match-type">Match in content</span>
              <% } %>

              <% if (matchInImage) { %>
                <% if (matchInTitle || matchInContent) { %> • <% } %>
                <span class="match-type">Match in image: </span><%= matchedImageName %>
              <% } %>
            </div>

            <% if (!note.getCategories().isEmpty()) { %>
              <div class="note-categories">
                <% for (String category : note.getCategories()) { %>
                  <span class="category-tag"><%= category %></span>
                <% } %>
              </div>
            <% } %>
          </div>
        </div>
      <%
        }
      } else {
      %>
        <div class="empty-state">
          <p>No results found for "<%= request.getAttribute("query") %>".</p>
          <p>Try using different keywords or check your spelling.</p>
          <a href="index.html" class="btn primary">Back to Home</a>
        </div>
      <%
        }
      %>
    </div>
  </div>
</body>
</html>
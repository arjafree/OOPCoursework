/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/11.0.4
 * Generated at: 2025-03-20 10:29:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import uk.ac.ucl.model.Directory;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.Category;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports,
                 org.apache.jasper.runtime.JspSourceDirectives {


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

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.LinkedHashSet<>(4);
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = new java.util.LinkedHashSet<>(8);
    _jspx_imports_classes.add("uk.ac.ucl.model.Category");
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("uk.ac.ucl.model.Note");
    _jspx_imports_classes.add("java.io.IOException");
    _jspx_imports_classes.add("uk.ac.ucl.model.Directory");
    _jspx_imports_classes.add("java.util.ArrayList");
  }

  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public boolean getErrorOnELNotFound() {
    return false;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final jakarta.servlet.jsp.PageContext pageContext;
    jakarta.servlet.http.HttpSession session = null;
    final jakarta.servlet.ServletContext application;
    final jakarta.servlet.ServletConfig config;
    jakarta.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    jakarta.servlet.jsp.JspWriter _jspx_out = null;
    jakarta.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("<head>\n");
      out.write("  <meta charset=\"UTF-8\">\n");
      out.write("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("  <title>Notes App</title>\n");
      out.write("  <link rel=\"stylesheet\" href=\"styles.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("  <div class=\"container\">\n");
      out.write("    <header>\n");
      out.write("      <h1>Notes App</h1>\n");
      out.write("      <div class=\"header-actions\">\n");
      out.write("        <a href=\"createDirectory\" class=\"btn primary\">\n");
      out.write("          <span class=\"icon\">+</span> New Directory\n");
      out.write("        </a>\n");
      out.write("        <a href=\"createCategory\" class=\"btn secondary\">\n");
      out.write("          <span class=\"icon\">+</span> New Category\n");
      out.write("        </a>\n");
      out.write("        <a href=\"createNote\" class=\"btn secondary\">\n");
      out.write("          <span class=\"icon\">+</span> New Note\n");
      out.write("        </a>\n");
      out.write("      </div>\n");
      out.write("    </header>\n");
      out.write("\n");
      out.write("    <div class=\"main-content\">\n");
      out.write("      <aside class=\"sidebar\">\n");
      out.write("        <div class=\"directory-tree\">\n");
      out.write("          <h2>Directories</h2>\n");
      out.write("          <ul class=\"tree-view\">\n");
      out.write("            ");

              Directory rootDirectory = (Directory) request.getAttribute("rootDirectory");
              if (rootDirectory != null) {
                // Render the root directory itself
                out.println("<li class=\"tree-item\">");
                out.println("  <div class=\"tree-item-content\">");
                out.println("    <span class=\"folder-icon\">📁</span>");
                out.println("    <a href=\"directory?path=" + rootDirectory.getPath() + "\">" + rootDirectory.getName() + "</a>");
                out.println("  </div>");
                out.println("</li>");

                // Render the subdirectories of the root directory
                for (Directory subdirectory : rootDirectory.getSubdirectories()) {
                  renderDirectoryTree(out, subdirectory, "");
                }
              } else {
            
      out.write("\n");
      out.write("              <li class=\"tree-item empty\">No directories available</li>\n");
      out.write("            ");

              }
            
      out.write("\n");
      out.write("          </ul>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"categories-section\">\n");
      out.write("          <h2>Categories</h2>\n");
      out.write("          <ul class=\"category-list\">\n");
      out.write("            ");

              ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
              if (categories != null && !categories.isEmpty()) {
                for (Category category : categories) {
            
      out.write("\n");
      out.write("              <li class=\"category-item\">\n");
      out.write("                <a href=\"displayNotes?category=");
      out.print( category.getName() );
      out.write("\">\n");
      out.write("                  <span class=\"tag-icon\">🏷️</span>\n");
      out.write("                  ");
      out.print( category.getName() );
      out.write("\n");
      out.write("                  <span class=\"note-count\">");
      out.print( category.getNoteIDs().size() );
      out.write("</span>\n");
      out.write("                </a>\n");
      out.write("              </li>\n");
      out.write("            ");

                }
              } else {
            
      out.write("\n");
      out.write("              <li class=\"category-item empty\">No categories available</li>\n");
      out.write("            ");

              }
            
      out.write("\n");
      out.write("          </ul>\n");
      out.write("        </div>\n");
      out.write("      </aside>\n");
      out.write("\n");
      out.write("      <main class=\"notes-container\">\n");
      out.write("        <div class=\"tabs\">\n");
      out.write("          <div class=\"tab-header\">\n");
      out.write("            <button class=\"tab-btn active\" data-tab=\"directories\">By Directory</button>\n");
      out.write("            <button class=\"tab-btn\" data-tab=\"categories\">By Category</button>\n");
      out.write("          </div>\n");
      out.write("\n");
      out.write("          <!-- Directory View Tab -->\n");
      out.write("          <div class=\"tab-content active\" id=\"directories\">\n");
      out.write("            <h2>Files by Directory</h2>\n");
      out.write("            \n");
      out.write("            <!-- Root Directory Notes Section -->\n");
      out.write("            ");

              if (rootDirectory != null) {
                List<Note> rootNotes = rootDirectory.getNotes();
                if (rootNotes != null && !rootNotes.isEmpty()) {
            
      out.write("\n");
      out.write("              <div class=\"directory-section\">\n");
      out.write("                <div class=\"directory-header\">\n");
      out.write("                  <h3 class=\"directory-title\">\n");
      out.write("                    <span class=\"folder-icon\">📁</span>\n");
      out.write("                    Root Directory\n");
      out.write("                  </h3>\n");
      out.write("                  <a href=\"directory?path=/\" class=\"view-all\">View Contents</a>\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("                <div class=\"notes-grid\">\n");
      out.write("                  ");

                    // Display up to 3 notes from root directory
                    int rootNotesToShow = Math.min(rootNotes.size(), 3);
                    for (int i = 0; i < rootNotesToShow; i++) {
                      Note note = rootNotes.get(i);
                  
      out.write("\n");
      out.write("                    <div class=\"note-card\" onclick=\"location.href='viewNote?id=");
      out.print( note.getId() );
      out.write("'\">\n");
      out.write("                      <div class=\"note-title\">\n");
      out.write("                        <span class=\"note-icon\">📝</span>\n");
      out.write("                        ");
      out.print( note.getName() != null ? note.getName() : note.getName() );
      out.write("\n");
      out.write("                      </div>\n");
      out.write("                      <div class=\"note-preview\">");
      out.print( note.getText().length() > 100 ? note.getText().substring(0, 100) + "..." : note.getText() );
      out.write("</div>\n");
      out.write("                      <div class=\"note-meta\">\n");
      out.write("                        <div class=\"note-categories\">\n");
      out.write("                          ");

                            for (String categoryName : note.getCategories()) {
                          
      out.write("\n");
      out.write("                            <span class=\"category-tag\">");
      out.print( categoryName );
      out.write("</span>\n");
      out.write("                          ");

                            }
                          
      out.write("\n");
      out.write("                        </div>\n");
      out.write("                      </div>\n");
      out.write("                    </div>\n");
      out.write("                  ");

                    }
                    // If there are more notes than we're showing
                    if (rootNotes.size() > 3) {
                  
      out.write("\n");
      out.write("                    <div class=\"more-notes-card\">\n");
      out.write("                      <a href=\"directory?path=/\" class=\"more-notes-link\">\n");
      out.write("                        + ");
      out.print( rootNotes.size() - 3 );
      out.write(" more notes\n");
      out.write("                      </a>\n");
      out.write("                    </div>\n");
      out.write("                  ");

                    }
                  
      out.write("\n");
      out.write("                </div>\n");
      out.write("              </div>\n");
      out.write("            ");

                }
              }
            
      out.write("\n");
      out.write("\n");
      out.write("            <!-- Subdirectories Sections -->\n");
      out.write("            ");

              if (rootDirectory != null && rootDirectory.getSubdirectories().size() > 0) {
                for (Directory dir : rootDirectory.getSubdirectories()) {
            
      out.write("\n");
      out.write("              <div class=\"directory-section\">\n");
      out.write("                <div class=\"directory-header\">\n");
      out.write("                  <h3 class=\"directory-title\">\n");
      out.write("                    <span class=\"folder-icon\">📁</span>\n");
      out.write("                    ");
      out.print( dir.getName() );
      out.write("\n");
      out.write("                  </h3>\n");
      out.write("                  <a href=\"directory?path=");
      out.print( dir.getPath() );
      out.write("\" class=\"view-all\">View Contents</a>\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("                <div class=\"notes-grid\">\n");
      out.write("                  ");

                    List<Note> dirNotes = dir.getNotes();
                    if (dirNotes != null && !dirNotes.isEmpty()) {
                      // Display up to 3 notes per directory on the index page
                      int notesToShow = Math.min(dirNotes.size(), 3);
                      for (int i = 0; i < notesToShow; i++) {
                        Note note = dirNotes.get(i);
                  
      out.write("\n");
      out.write("                    <div class=\"note-card\" onclick=\"location.href='viewNote?id=");
      out.print( note.getId() );
      out.write("'\">\n");
      out.write("                      <div class=\"note-title\">\n");
      out.write("                        <span class=\"note-icon\">📝</span>\n");
      out.write("                        ");
      out.print( note.getName() != null ? note.getName() : note.getName() );
      out.write("\n");
      out.write("                      </div>\n");
      out.write("                      <div class=\"note-preview\">");
      out.print( note.getText().length() > 100 ? note.getText().substring(0, 100) + "..." : note.getText() );
      out.write("</div>\n");
      out.write("                      <div class=\"note-meta\">\n");
      out.write("                        <div class=\"note-categories\">\n");
      out.write("                          ");

                            for (String categoryName : note.getCategories()) {
                          
      out.write("\n");
      out.write("                            <span class=\"category-tag\">");
      out.print( categoryName );
      out.write("</span>\n");
      out.write("                          ");

                            }
                          
      out.write("\n");
      out.write("                        </div>\n");
      out.write("                      </div>\n");
      out.write("                    </div>\n");
      out.write("                  ");

                      }
                      // If there are more notes than we're showing
                      if (dirNotes.size() > 3) {
                  
      out.write("\n");
      out.write("                    <div class=\"more-notes-card\">\n");
      out.write("                      <a href=\"directory?path=");
      out.print( dir.getPath() );
      out.write("\" class=\"more-notes-link\">\n");
      out.write("                        + ");
      out.print( dirNotes.size() - 3 );
      out.write(" more notes\n");
      out.write("                      </a>\n");
      out.write("                    </div>\n");
      out.write("                  ");

                      }
                    } else {
                  
      out.write("\n");
      out.write("                    <div class=\"empty-notes-card\">\n");
      out.write("                      <p>No notes in this directory</p>\n");
      out.write("                      <a href=\"createNote?directory=");
      out.print( dir.getPath() );
      out.write("\" class=\"btn secondary small\">\n");
      out.write("                        <span class=\"icon\">+</span> Add Note\n");
      out.write("                      </a>\n");
      out.write("                    </div>\n");
      out.write("                  ");

                    }
                  
      out.write("\n");
      out.write("                </div>\n");
      out.write("              </div>\n");
      out.write("            ");

                }
              } else if (rootDirectory != null && rootDirectory.getNotes().isEmpty()) {
            
      out.write("\n");
      out.write("              <div class=\"empty-state\">\n");
      out.write("                <p>No notes available. Create your first note to get started!</p>\n");
      out.write("                <a href=\"createNote\" class=\"btn primary\">Create Note</a>\n");
      out.write("              </div>\n");
      out.write("            ");

              } else if (rootDirectory == null) {
            
      out.write("\n");
      out.write("              <div class=\"empty-state\">\n");
      out.write("                <p>No directories available. Create your first directory to get started!</p>\n");
      out.write("                <a href=\"createDirectory\" class=\"btn primary\">Create Directory</a>\n");
      out.write("              </div>\n");
      out.write("            ");

              }
            
      out.write("\n");
      out.write("          </div>\n");
      out.write("\n");
      out.write("          <!-- Category View Tab -->\n");
      out.write("          <div class=\"tab-content\" id=\"categories\">\n");
      out.write("            <h2>Notes by Category</h2>\n");
      out.write("\n");
      out.write("            ");

              if (categories != null && !categories.isEmpty()) {
                List<List<Note>> listOflistOfnotes = (List<List<Note>>) request.getAttribute("listOflistOfnotes");
                for (int i = 0; i < categories.size(); i++) {
                  Category category = categories.get(i);
                  List<Note> categoryNotes = listOflistOfnotes.get(i);
            
      out.write("\n");
      out.write("              <div class=\"category-section\">\n");
      out.write("                <div class=\"category-header\">\n");
      out.write("                  <h3 class=\"category-title\">\n");
      out.write("                    <span class=\"tag-icon\">🏷️</span>\n");
      out.write("                    ");
      out.print( category.getName() );
      out.write("\n");
      out.write("                  </h3>\n");
      out.write("                  <a href=\"displayNotes?category=");
      out.print( category.getName() );
      out.write("\" class=\"view-all\">View All</a>\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("                <div class=\"notes-grid\">\n");
      out.write("                  ");

                    if (categoryNotes != null && !categoryNotes.isEmpty()) {
                      // Display up to 3 notes per category on the index page
                      int notesToShow = Math.min(categoryNotes.size(), 3);
                      for (int j = 0; j < notesToShow; j++) {
                        Note note = categoryNotes.get(j);
                  
      out.write("\n");
      out.write("                    <div class=\"note-card\" onclick=\"location.href='viewNote?id=");
      out.print( note.getId() );
      out.write("'\">\n");
      out.write("                      <div class=\"note-title\">\n");
      out.write("                        <span class=\"note-icon\">📝</span>\n");
      out.write("                        ");
      out.print( note.getName() != null ? note.getName() : note.getName() );
      out.write("\n");
      out.write("                      </div>\n");
      out.write("                      <div class=\"note-preview\">");
      out.print( note.getText().length() > 100 ? note.getText().substring(0, 100) + "..." : note.getText() );
      out.write("</div>\n");
      out.write("                      <div class=\"note-meta\">\n");
      out.write("                        <div class=\"note-directory\">\n");
      out.write("                          <span class=\"directory-path\">📂\n");
      out.write("                            ");

                              String dirPath = note.getDirectoryPath();
                            
      out.write("\n");
      out.write("                            ");
      out.print( dirPath );
      out.write("\n");
      out.write("                          </span>\n");
      out.write("                        </div>\n");
      out.write("                      </div>\n");
      out.write("                    </div>\n");
      out.write("                  ");

                      }
                      // If there are more notes than we're showing
                      if (categoryNotes.size() > 3) {
                  
      out.write("\n");
      out.write("                    <div class=\"more-notes-card\">\n");
      out.write("                      <a href=\"displayNotes?category=");
      out.print( category.getName() );
      out.write("\" class=\"more-notes-link\">\n");
      out.write("                        + ");
      out.print( categoryNotes.size() - 3 );
      out.write(" more notes\n");
      out.write("                      </a>\n");
      out.write("                    </div>\n");
      out.write("                  ");

                      }
                    } else {
                  
      out.write("\n");
      out.write("                    <div class=\"empty-notes-card\">\n");
      out.write("                      <p>No notes in this category</p>\n");
      out.write("                      <a href=\"createNote?category=");
      out.print( category.getName() );
      out.write("\" class=\"btn secondary small\">\n");
      out.write("                        <span class=\"icon\">+</span> Add Note\n");
      out.write("                      </a>\n");
      out.write("                    </div>\n");
      out.write("                  ");

                    }
                  
      out.write("\n");
      out.write("                </div>\n");
      out.write("              </div>\n");
      out.write("            ");

                }
              } else {
            
      out.write("\n");
      out.write("              <div class=\"empty-state\">\n");
      out.write("                <p>No categories available. Create your first category to get started!</p>\n");
      out.write("                <a href=\"createCategory\" class=\"btn primary\">Create Category</a>\n");
      out.write("              </div>\n");
      out.write("            ");

              }
            
      out.write("\n");
      out.write("          </div>\n");
      out.write("        </div>\n");
      out.write("      </main>\n");
      out.write("    </div>\n");
      out.write("  </div>\n");
      out.write("\n");
      out.write("  <script>\n");
      out.write("    // Tab switching functionality\n");
      out.write("    document.querySelectorAll('.tab-btn').forEach(button => {\n");
      out.write("      button.addEventListener('click', () => {\n");
      out.write("        // Remove active class from all buttons and content\n");
      out.write("        document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));\n");
      out.write("        document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));\n");
      out.write("\n");
      out.write("        // Add active class to clicked button\n");
      out.write("        button.classList.add('active');\n");
      out.write("\n");
      out.write("        // Show corresponding content\n");
      out.write("        const tabId = button.getAttribute('data-tab');\n");
      out.write("        document.getElementById(tabId).classList.add('active');\n");
      out.write("      });\n");
      out.write("    });\n");
      out.write("\n");
      out.write("    // Toggle directory tree items\n");
      out.write("    document.querySelectorAll('.toggle-icon').forEach(icon => {\n");
      out.write("      icon.addEventListener('click', function(e) {\n");
      out.write("        e.preventDefault();\n");
      out.write("        e.stopPropagation();\n");
      out.write("        const treeItem = this.closest('.tree-item');\n");
      out.write("        treeItem.classList.toggle('expanded');\n");
      out.write("        this.textContent = treeItem.classList.contains('expanded') ? '▼' : '▶';\n");
      out.write("      });\n");
      out.write("    });\n");
      out.write("  </script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
      out.write("\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

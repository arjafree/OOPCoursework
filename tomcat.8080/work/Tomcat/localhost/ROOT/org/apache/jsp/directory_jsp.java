/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/11.0.4
 * Generated at: 2025-03-20 14:08:40 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;
import uk.ac.ucl.model.Directory;
import uk.ac.ucl.model.Note;
import java.util.ArrayList;

public final class directory_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports,
                 org.apache.jasper.runtime.JspSourceDirectives {

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
    _jspx_imports_classes = new java.util.LinkedHashSet<>(4);
    _jspx_imports_classes.add("uk.ac.ucl.model.Note");
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("<head>\n");
      out.write("  <meta charset=\"UTF-8\">\n");
      out.write("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("  <title>Directory Contents</title>\n");
      out.write("  <link rel=\"stylesheet\" href=\"styles.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("  <div class=\"container\">\n");
      out.write("    ");

      Directory directory = (Directory) request.getAttribute("directory");
      if (directory != null) {
        String parentPath = "";
        Directory parent = (Directory) request.getAttribute("parentDirectory");
        ArrayList<Note> notes = (ArrayList<Note>) request.getAttribute("notes");
        if (parent != null) {
          parentPath = parent.getPath();
        }
    
      out.write("\n");
      out.write("    <header>\n");
      out.write("      <h1>\n");
      out.write("        <span class=\"folder-icon\">📁</span>\n");
      out.write("        ");
      out.print( directory.getPath().equals("/") ? "Root Directory" : directory.getName() );
      out.write("\n");
      out.write("      </h1>\n");
      out.write("      <div class=\"header-actions\">\n");
      out.write("        ");
 if (!parentPath.isEmpty()) { 
      out.write("\n");
      out.write("          <a href=\"directory?path=");
      out.print( parentPath );
      out.write("\" class=\"btn secondary\">\n");
      out.write("            <span class=\"icon\">↑</span> Parent Directory\n");
      out.write("          </a>\n");
      out.write("        ");
 } else if (!directory.getPath().equals("/")) { 
      out.write("\n");
      out.write("          <a href=\"index.html\" class=\"btn secondary\">\n");
      out.write("            <span class=\"icon\">←</span> Back to Home\n");
      out.write("          </a>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        <a href=\"createDirectory?parent=");
      out.print( directory.getPath() );
      out.write("\" class=\"btn secondary\">\n");
      out.write("          <span class=\"icon\">+</span> New Subdirectory\n");
      out.write("        </a>\n");
      out.write("        <a href=\"createNote?directory=");
      out.print( directory.getPath() );
      out.write("\" class=\"btn primary\">\n");
      out.write("          <span class=\"icon\">+</span> New Note\n");
      out.write("        </a>\n");
      out.write("      </div>\n");
      out.write("    </header>\n");
      out.write("\n");
      out.write("    ");
 if (!directory.getPath().equals("/")) { 
      out.write("\n");
      out.write("    <div class=\"directory-path-nav\">\n");
      out.write("      <span class=\"path-label\">Path:</span>\n");
      out.write("      ");

        String[] pathParts = directory.getPath().split("/");
        String currentPath = "";
        for (int i = 0; i < pathParts.length; i++) {
          if (pathParts[i].isEmpty()) {
            if (i == 0) {
              // Root directory
              currentPath = "/";
      
      out.write("\n");
      out.write("              <a href=\"directory?path=/\" class=\"path-part\">Root</a>\n");
      out.write("      ");

              if (pathParts.length > 1) {
      
      out.write("\n");
      out.write("                <span class=\"path-separator\">/</span>\n");
      out.write("      ");

              }
              continue;
            } else {
              continue;
            }
          }
          currentPath += "/" + pathParts[i];
          boolean isLast = (i == pathParts.length - 1);
      
      out.write("\n");
      out.write("        <a href=\"directory?path=");
      out.print( currentPath );
      out.write("\" class=\"path-part ");
      out.print( isLast ? "current" : "" );
      out.write("\">\n");
      out.write("          ");
      out.print( pathParts[i] );
      out.write("\n");
      out.write("        </a>\n");
      out.write("        ");
 if (!isLast) { 
      out.write("\n");
      out.write("          <span class=\"path-separator\">/</span>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("      ");

        }
      
      out.write("\n");
      out.write("    </div>\n");
      out.write("    ");
 } 
      out.write("\n");
      out.write("\n");
      out.write("    <div class=\"main-content single-column\">\n");
      out.write("      <main class=\"notes-container\">\n");
      out.write("        ");
 if (directory.getSubdirectories() != null && !directory.getSubdirectories().isEmpty()) { 
      out.write("\n");
      out.write("          <section class=\"subdirectories-section\">\n");
      out.write("            <h2>Subdirectories</h2>\n");
      out.write("            <div class=\"subdirectories-grid\">\n");
      out.write("              ");
 for (Directory subdir : directory.getSubdirectories()) { 
      out.write("\n");
      out.write("                <div class=\"directory-card\" onclick=\"location.href='directory?path=");
      out.print( subdir.getPath() );
      out.write("'\">\n");
      out.write("                  <div class=\"directory-card-title\">\n");
      out.write("                    <span class=\"folder-icon\">📁</span>\n");
      out.write("                    ");
      out.print( subdir.getName() );
      out.write("\n");
      out.write("                  </div>\n");
      out.write("                  <div class=\"directory-card-meta\">\n");
      out.write("                    ");

                      int noteCount = 0;
                      try {
                        noteCount = subdir.getNotes().size();
                      } catch (Exception e) {
                        // If the method doesn't exist yet
                      }
                    
      out.write("\n");
      out.write("                    <span class=\"note-count\">");
      out.print( noteCount );
      out.write(" notes</span>\n");
      out.write("                  </div>\n");
      out.write("                </div>\n");
      out.write("              ");
 } 
      out.write("\n");
      out.write("            </div>\n");
      out.write("          </section>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("\n");
      out.write("        <section class=\"notes-section\">\n");
      out.write("          <h2>Notes</h2>\n");
      out.write("          <div class=\"notes-grid\">\n");
      out.write("            ");

              if (notes != null && !notes.isEmpty()) {
                for (Note note : notes) {
            
      out.write("\n");
      out.write("              <div class=\"note-card\" onclick=\"location.href='viewNote?id=");
      out.print( note.getId() );
      out.write("'\">\n");
      out.write("                <div class=\"note-title\">\n");
      out.write("                  <span class=\"note-icon\">📝</span>\n");
      out.write("                  ");
      out.print( note.getName() != null ? note.getName() : note.getName() );
      out.write("\n");
      out.write("                </div>\n");
      out.write("                <div class=\"note-preview\">");
      out.print( note.getText().length() > 100 ? note.getText().substring(0, 100) + "..." : note.getText() );
      out.write("</div>\n");
      out.write("                <div class=\"note-meta\">\n");
      out.write("                  <div class=\"note-categories\">\n");
      out.write("                    ");

                      for (String categoryName : note.getCategories()) {
                    
      out.write("\n");
      out.write("                      <span class=\"category-tag\">");
      out.print( categoryName );
      out.write("</span>\n");
      out.write("                    ");

                      }
                    
      out.write("\n");
      out.write("                  </div>\n");
      out.write("                </div>\n");
      out.write("              </div>\n");
      out.write("            ");

                }
              } else {
            
      out.write("\n");
      out.write("              <div class=\"empty-notes-card\">\n");
      out.write("                <p>No notes in this directory</p>\n");
      out.write("                <a href=\"createNote?directory=");
      out.print( directory.getPath() );
      out.write("\" class=\"btn secondary\">\n");
      out.write("                  <span class=\"icon\">+</span> Add Note\n");
      out.write("                </a>\n");
      out.write("              </div>\n");
      out.write("            ");

              }
            
      out.write("\n");
      out.write("          </div>\n");
      out.write("        </section>\n");
      out.write("      </main>\n");
      out.write("    </div>\n");
      out.write("    ");

      } else {
    
      out.write("\n");
      out.write("    <div class=\"empty-state\">\n");
      out.write("      <p>Directory not found.</p>\n");
      out.write("      <a href=\"index.html\" class=\"btn primary\">Back to Home</a>\n");
      out.write("    </div>\n");
      out.write("    ");

      }
    
      out.write("\n");
      out.write("  </div>\n");
      out.write("</body>\n");
      out.write("</html>");
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

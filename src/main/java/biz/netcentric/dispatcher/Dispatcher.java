package biz.netcentric.dispatcher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Dispatcher abstract class provides the structure and operations to 
 * exucute and implement  the Dispatcher
 * @author sebas.monsalve@gmail.com
 *
 */
public abstract class Dispatcher {
	
	protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
 
    public void init(
      ServletContext servletContext,
      HttpServletRequest servletRequest,
      HttpServletResponse servletResponse) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
    }
 
    /**
     * Implementation to reads the requested file, parses the HTML, and processes the transformations.
     * @throws ServletException  if there is a problem in the Servlet
     * @throws IOException if there is a problem parsing the HTML file
     */
    public abstract void process() throws ServletException, IOException;
 
    /**
     * Write the new HTML processed
     * @param page with the new HTML
     * @throws IOException if there is a problem creating the new HTML
     * @throws ServletException if there is a problem in the Servlet
     */
    protected void renderPage(String page) throws ServletException, IOException{
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(page);
    }
    
    /**
     * Include a new code in the target and dispatch the response
     * @param target 
     * @param includeText text that is append in the target
     * @throws IOException if there is a problem forward the request
     * @throws ServletException if there is a problem in the Servlet
     */
    protected void include(String target, String includeText) throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        out.println(includeText);
        target = String.format("/WEB-INF/%s", target);
        RequestDispatcher dispatcher = request.getRequestDispatcher(target);
        dispatcher.include(request, response);
    }
    
    /**
     * Forward the target and dispatch the response
     * @param target
     * @throws ServletException if there is a problem in the Servlet
     * @throws IOException if there is a problem forward the request
     */
    protected void forward(String target) throws ServletException, IOException {
        target = String.format("/WEB-INF/%s", target);
        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }

}

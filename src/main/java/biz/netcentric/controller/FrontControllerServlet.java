package biz.netcentric.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.netcentric.dispatcher.Dispatcher;
import biz.netcentric.dispatcher.SlightlyDispatcher;
import biz.netcentric.dispatcher.UnknownDispatcher;

/**
 * Servlet implementation class FrontControllerServlet
 * The controller is typically the initial contact point for handling a request. 
 * The controller delegates to a dispatcher to do view management.
 */
public class FrontControllerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontControllerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Dispatcher dispatcher = processRequest(request, response);
		dispatcher.init(getServletContext(), request, response);
		dispatcher.process(); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * Validate the request and select the correct Dispatcher to try the request.
	 * @param request
	 * @param response
	 * @return Dispatcher
	 */
	private Dispatcher processRequest(HttpServletRequest request, HttpServletResponse response) {
		Dispatcher dispatcher;
	
		String uri = request.getRequestURI();
		String fileName = uri.substring(uri.lastIndexOf('/'), uri.length());	
		String fullPath = getServletContext().getRealPath(fileName);
				
		File file = new File(fullPath);
		
		if(file.exists() && !file.isDirectory()) { 
			dispatcher = new SlightlyDispatcher();
		}else{
			dispatcher = new UnknownDispatcher();
		}
		
		return dispatcher;
	}
	

}

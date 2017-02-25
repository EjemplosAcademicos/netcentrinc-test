package biz.netcentric.dispatcher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import biz.netcentric.Person;
import biz.netcentric.slyghtly.EngineJavaScript;
import biz.netcentric.slyghtly.NashornEngine;
import biz.netcentric.slyghtly.SlyghtlyTransformer;

import javax.script.ScriptException;

/**
 * The SlightlyDispatcher class provides operations to transform the different specification 
 * and return the final product
 * @author sebas.monsalve@gmial.com
 *
 */
public class SlightlyDispatcher extends Dispatcher{
	
	private EngineJavaScript engineJavaScript;
	private SlyghtlyTransformer transformer;
	
	public SlightlyDispatcher(){
		
	}

	@Override
	public void process() throws ServletException, IOException {
		String page = parseSlightly();
		renderPage(page);
	}
	
	/**
	 * Identify the different Specification and aply the trasformetion rules.
	 * @return The new page to render
	 * @throws ServletException  if there is a problem in the Servlet
     * @throws IOException if there is a problem parsing the HTML file
	 */
	private String parseSlightly() throws IOException, ServletException {
		
		String uri = request.getRequestURI();
		String fileName = uri.substring(uri.lastIndexOf('/'), uri.length());		
		String fullPath = context.getRealPath(fileName);
		File fileHTML = new File(fullPath);
		
		//parce the HTML file to Object
		Document doc = Jsoup.parse(fileHTML,"utf-8");	
		
		try {
			
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("request", request);
			transformer = new SlyghtlyTransformer(variables);
			
			Elements scripts = doc.select("script[type=server/javascript");
			transformer.transform_script(scripts);
			
			//data-if
			Elements data_if = doc.select("[data-if]");
			transformer.transform_data_if(data_if);
			
			//find data-for-x
			Elements data_for_x = doc.select("[^data-for]");
			transformer.transform_data_for(data_for_x);
			
			//find $expression
			Elements $expressions = doc.getElementsContainingText("${");
			transformer.transforme_$expression($expressions);
								
			
		} catch (ScriptException e) {
			e.printStackTrace(); 
			request.setAttribute("message", "Fail, evaluating JavaScript expression");
			forward("error.jsp");
		} catch (Exception e){
			e.printStackTrace(); 
			request.setAttribute("message", "Fail, evaluating HTML expression");
			forward("error.jsp");
		}
		
		return doc.toString();
	}

}

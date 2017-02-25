package biz.netcentric.slyghtly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The SlightlyDispatcher class provides operations to transform the different specification 
 * @author sebas.monsalve@gmail.com
 *
 */
public class SlyghtlyTransformer {
	
	private EngineJavaScript engineJavaScript;
	
	public SlyghtlyTransformer(Map<String, Object> variables) throws ScriptException{
		engineJavaScript = new NashornEngine();
		engineJavaScript.putVariablesIntoEngine(variables);
		engineJavaScript.evalExpresion("load(\"nashorn:mozilla_compat.js\")");
	}
	
	public void transform_script(Elements scripts) throws ScriptException{
		for(Element src: scripts){
			engineJavaScript.evalExpresion(src.data());
			src.remove();
		}
	}

	public void transform_data_if(Elements data_if) throws ScriptException{
		for(Element element: data_if){
			
			if((boolean) engineJavaScript.evalExpresion(element.attr("data-if"))){
				element.removeAttr("data-if");
			}else{
				element.remove();
			}
		}
	}

	public void transform_data_for(Elements data_for_x)  throws ScriptException{
		for(Element element: data_for_x){
			
			Attributes at = element.attributes();
			String text = element.text().substring(0,element.text().indexOf("${"));
			String tag  = element.tagName();
		    for (Attribute a : at) {		    	
		    	List list = (List) engineJavaScript.evalExpresion(a.getValue());
		    	for(int i=0; i<list.size(); i++){
		    		element.before("<"+tag+">"+text+list.get(i)+"</"+tag+">");
		    	}
		    }
			element.remove();
		}
	}

	public void transforme_$expression(Elements $expressions) throws ScriptException{
		for(Element element : $expressions){
			if(element.childNodeSize()==1){
									
				//find inside the values of attributes
				Attributes at = element.attributes();
			    for (Attribute a : at) {	
			    	Matcher m = Pattern.compile("\\$\\{(.*?)}").matcher(a.getValue());
					while(m.find()) {
				        String result= (String) engineJavaScript.evalExpresion(m.group(1));
				        a.setValue(result);
				    }
			    }
				
			    //find inside the element bodies
			    Matcher m = Pattern.compile("\\$\\{(.*?)}").matcher(element.text());
				while(m.find()) {
			        String result= (String) engineJavaScript.evalExpresion(m.group(1));
			        element.text(element.text().replaceAll("\\$\\{"+m.group(1)+"}", result));
			    }
			    
			}
		}
	}
}

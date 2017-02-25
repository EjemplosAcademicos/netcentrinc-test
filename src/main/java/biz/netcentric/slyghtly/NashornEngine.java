package biz.netcentric.slyghtly;

import java.util.Map;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

public class NashornEngine extends EngineJavaScript{
	
	private static final String NASHORN_ENGINE_NAME = "nashorn";
	private ScriptEngine nashornEngine;
	private Bindings bindings;
	
	public NashornEngine(){
		//init engine javascript
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		nashornEngine = scriptEngineManager.getEngineByName(NASHORN_ENGINE_NAME);
	}
	
	@Override
	public Object evalExpresion(String expression) throws ScriptException {
		return nashornEngine.eval(expression);
	}
	
	@Override
	public void putVariablesIntoEngine(Map variables) {
		//set variables to the javascript
		bindings = new SimpleBindings();
		variables.forEach((k,v) -> bindings.put((String) k, v));
		nashornEngine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
	}

}

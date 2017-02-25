package biz.netcentric.slyghtly;

import java.util.Map;

import javax.script.ScriptException;

/**
 * The EngineJavaScript abstract class provides the structure and operations for a
 * Engine JavaScript
 * @author sebas.monsalve@gmail.com
 *
 */
public abstract class EngineJavaScript {

	/**
	 * Let evaluate Java Script Expression
	 * @param expression
	 * @return
	 * @throws ScriptException
	 */
	public abstract Object evalExpresion(String expression) throws ScriptException;
	
	/**
	 * Put variables java into the engine.
	 * @param variables java
	 */
	public abstract void putVariablesIntoEngine(Map variables);
}

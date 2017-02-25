import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import biz.netcentric.slyghtly.SlyghtlyTransformer;

@RunWith(Parameterized.class)
public class SlightlyTransformerTest{
	
	private SlyghtlyTransformer transformer;
	private Elements elements;
	
	public SlightlyTransformerTest(Elements elements) throws IOException{
		this.elements = elements;	
	}

	@Parameters
	public static Collection<Object[]> nums(){
		Object[][] nums = new Object[][]{
			{new Elements()}
		};
		return Arrays.asList(nums);
	}
	
	@Before
	public void constructor() throws ScriptException{
		Map<String, Object> variables = new HashMap<String, Object>();
		//variables.put("request", request);
		transformer = new SlyghtlyTransformer(variables);
		
		
	}
		
	@After
	public void destroy(){}
	
	@Test
	public void transformerScriptTest(){
		try {
			transformer.transform_script(elements);
			assertTrue(true);
		} catch (ScriptException e) {
			fail("Don't excepted this ScriptException");
		} catch(Exception e){
			fail("Don't excepted this exception");
		}
	}
	
	@Test
	public void transformerDataIfTest(){
		try {
			transformer.transform_data_if(elements);
			assertTrue(true);
		} catch (ScriptException e) {
			fail("Don't excepted this ScriptException");
		} catch(Exception e){
			fail("Don't excepted this exception");
		}
	}
	
	@Test
	public void transformerDataForTest(){
		try {
			transformer.transform_data_for(elements);
			assertTrue(true);
		} catch (ScriptException e) {
			fail("Don't excepted this ScriptException");
		} catch(Exception e){
			fail("Don't excepted this exception");
		}
	}
	
	@Test
	public void transformer$ExpressionTest(){
		try {
			transformer.transforme_$expression(elements);
			assertTrue(true);
		} catch (ScriptException e) {
			fail("Don't excepted this ScriptException");
		} catch(Exception e){
			fail("Don't excepted this exception");
		}
	}


}

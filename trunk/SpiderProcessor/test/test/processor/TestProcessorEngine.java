package test.processor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.processor.ProcessorEngine;

public class TestProcessorEngine {

	private ProcessorEngine engine = null;
	private String dir = "D:/Workspaces/Aries/SpiderProcessor/xml";

	private TestProcessorEngine() throws Exception {
		// initialize engine
		engine = new ProcessorEngine();
		engine.load(new File(dir));

		// data
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("url", "http://news.163.com/243afaf423.html");
		data.put("html", "asdfghjkjuyfjfjgreoijijdsaf;ljwerpo08143579473");

		// process
		engine.process(data);
	}

	public static void main(String[] args) {
		try {
			new TestProcessorEngine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

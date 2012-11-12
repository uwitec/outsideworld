package test.processor;

import java.io.File;

import com.processor.ProcessorEngine;

public class TestProcessorEngine {

	private ProcessorEngine engine = null;
	private String dir = "D:/Workspaces/Aries/SpiderProcessor/xml";

	private TestProcessorEngine() throws Exception {
		engine = new ProcessorEngine();
		engine.load(new File(dir));
		engine.start();
	}

	public static void main(String[] args) {
		try {
			new TestProcessorEngine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

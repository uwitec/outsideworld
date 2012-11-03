package com.processor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.processor.model.ProcessorFactoryType;
import com.processor.model.ProcessorType;
import com.processor.model.ProcessorsType;

public class ProcessorFactory {

	public static List<Processor> createProcessors(ProcessorsType processors) {
		List<Processor> processorList = new ArrayList<Processor>();
		if (processors.getProcessor() != null) {
			for (ProcessorType processor : processors.getProcessor()) {
				processorList.add(new Processor(processor));
			}
		}
		return processorList;
	}

	public static List<Processor> createProcessors(
			ProcessorFactoryType processorFactory) {
		List<Processor> processorList = new ArrayList<Processor>();
		File file = new File(processorFactory.getPath());
		load(processorList, processorFactory.getProcessor(), file);
		return processorList;
	}

	private static void load(List<Processor> processorList,
			ProcessorType processorType, File path) {
		if (path.isFile()) {
			parse(processorList, processorType, path);
		} else if (path.isDirectory()) {
			File[] files = path.listFiles();
			for (File file : files) {
				load(processorList, processorType, file);
			}
		}
	}

	private static void parse(List<Processor> processorList,
			ProcessorType processorType, File file) {
		Map<String, String> params = new HashMap<String, String>();
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(file));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Object key : properties.keySet()) {
			params.put(key.toString(), properties.getProperty(key.toString()));
		}

		Processor processor = new Processor(processorType);
		processor.setParams(params);
		processorList.add(processor);
	}
}

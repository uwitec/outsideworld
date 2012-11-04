package com.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.processor.handler.impl.HandlerAPI;
import com.processor.model.ProcessorsType;

public class ProcessorEngine {

	private static JAXBContext jaxb = null;
	private static Unmarshaller unmarshaller = null;
	private static List<Processor> processors = null;
	private static ThreadPoolExecutor executor = null;
	private static ArrayBlockingQueue<Runnable> bufferQueue = null;
	private static ApplicationContext applicationContext = null;

	private int minThreads = 5;
	private int maxThread = 10;
	private int expireTime = 5;
	private int bufferSize = 100;
	private String config = "config/*.xml";

	public ProcessorEngine() throws Exception {
		jaxb = JAXBContext.newInstance("com.processor.model");
		unmarshaller = jaxb.createUnmarshaller();
		processors = new ArrayList<Processor>();
		bufferQueue = new ArrayBlockingQueue<Runnable>(bufferSize);
		executor = new ThreadPoolExecutor(minThreads, maxThread, expireTime,
				TimeUnit.SECONDS, bufferQueue,
				new ThreadPoolExecutor.AbortPolicy());
		applicationContext = new FileSystemXmlApplicationContext(config);
	}

	public static HandlerAPI getHandler(String name) {
		return applicationContext.getBean(name, HandlerAPI.class);
	}

	public void load(File path) {
		if (path.isFile()) {
			parse(path);
		} else if (path.isDirectory()) {
			File[] files = path.listFiles();
			for (File file : files) {
				load(file);
			}
		}
	}

	public void process(Map<String, Object> data) {
		for (Processor processor : processors) {
			processor.setData(data);
			executor.execute(processor);
		}
	}

	@SuppressWarnings("unchecked")
	private void parse(File file) {
		try {
			JAXBElement<ProcessorsType> jaxbElement = (JAXBElement<ProcessorsType>) unmarshaller
					.unmarshal(file);
			ProcessorsType processorsType = jaxbElement.getValue();
			processors
					.addAll(ProcessorFactory.createProcessors(processorsType));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

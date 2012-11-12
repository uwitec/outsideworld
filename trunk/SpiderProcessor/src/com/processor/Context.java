package com.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.processor.handler.BaseHandler;

public class Context {

	private static final Log log = LogFactory.getLog(Context.class);

	private boolean failsafe = false;
	private Map<String, Object> elements = new HashMap<String, Object>();;
	private Map<String, Set<String>> collector = new HashMap<String, Set<String>>();
	private Map<String, Exception> errors = new HashMap<String, Exception>();

	public void setData(Map<String, Object> data) {
		// set elements
		this.elements.clear();
		for (String name : data.keySet()) {
			elements.put(name, data.get(name));
		}
		// clear collector
		this.collector = new HashMap<String, Set<String>>();
		// clear errors
		this.errors.clear();
	}

	public void addElement(String name, Object value) {
		elements.put(name, value);
	}

	public void addCollector(String collectorName, String elementName) {
		Set<String> collection = collector.get(collector);
		if (collection == null) {
			collection = new HashSet<String>();
		}
		collection.add(elementName);
	}

	public Set<String> getCollector(String collectorName) {
		return collector.get(collectorName);
	}

	public Object getParam(String name) {
		return elements.get(name);
	}

	public void setFailsafe(boolean failsafe) {
		this.failsafe = failsafe;
	}

	public boolean isFailsafe() {
		return failsafe;
	}

	public void error(BaseHandler handler, Exception e) {
		errors.put(handler.getName() + ":" + new Date().toString(), e);
	}

	public int getErrors() {
		return errors.size();
	}

	public void log() {
		if (errors.size() > 0) {
			for (String name : errors.keySet()) {
				log.error("handler" + name + " error", errors.get(name));
			}
		} else {
			log.info("process " + this.toString() + " successfully");
		}
	}
}

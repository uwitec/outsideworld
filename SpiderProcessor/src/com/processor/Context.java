package com.processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Context {

	private Map<String, Object> environment = null;

	private Set<String> outputSet = null;

	public Context(Map<String, Object> data) {
		this.environment = new HashMap<String, Object>(data);
		this.outputSet = new HashSet<String>();
	}

	public void addElement(String name, Object value) {
		environment.put(name, value);
	}

	public void addOutput(String name, Object value) {
		addElement(name, value);
		outputSet.add(name);
	}

	public Object getParam(String name) {
		return environment.get(name);
	}
}

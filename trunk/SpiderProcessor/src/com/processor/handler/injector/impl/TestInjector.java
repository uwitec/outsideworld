package com.processor.handler.injector.impl;

import java.util.HashMap;
import java.util.Map;

import com.processor.handler.api.Injector;

public class TestInjector implements Injector {

	@Override
	public void initialize(Map<String, Object> params) {

	}

	@Override
	public Map<String, Object> getNext() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("url", "http://news.163.com/243afaf423.html");
		data.put("html", "asdfghjkjuyfjfjgreoijijdsaf;ljwerpo08143579473");

		return data;
	}

}

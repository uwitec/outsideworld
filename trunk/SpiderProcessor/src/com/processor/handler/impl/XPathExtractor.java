package com.processor.handler.impl;

import java.util.Map;

import com.processor.handler.api.Handler;

public class XPathExtractor implements Handler {

	public Object handle(Map<String, Object> params) {
		System.out.println("test handler");
		return "element";
	}
}

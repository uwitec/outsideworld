package com.processor.handler.impl;

import java.util.Map;

public class TestHandler implements HandlerAPI {

	public Object handle(Map<String, Object> params) {
		System.out.println("test handler");
		return "element";
	}
}

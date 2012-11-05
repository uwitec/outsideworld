package com.processor.handler.impl;

import java.util.Map;

import com.processor.handler.Handler;

public class TestHandler implements Handler {

	public Object handle(Map<String, Object> params) {
		System.out.println("test handler");
		return "element";
	}
}

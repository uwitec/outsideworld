package com.processor.handler.api;

import java.util.Map;

public interface Injector {

	public void initialize(Map<String, Object> params) throws Exception;

	public Map<String, Object> getNext() throws Exception;
}

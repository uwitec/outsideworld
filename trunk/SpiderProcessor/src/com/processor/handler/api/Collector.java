package com.processor.handler.api;

import java.util.Map;

public interface Collector {

	public void initialize(Map<String, Object> params) throws Exception;

	public void collect(Map<String, Object> data) throws Exception;
}

package com.processor.handler;

import java.util.Map;

public interface Injector {
	
	public void initialize (Map<String,Object> params);
	
	public Map<String,Object> next();
}

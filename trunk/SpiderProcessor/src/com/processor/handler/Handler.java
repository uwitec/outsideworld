package com.processor.handler;

import java.util.Map;

public interface Handler {

	public static final String COLLECTION = "COLLECTION";

	public Object handle(Map<String, Object> data);

}

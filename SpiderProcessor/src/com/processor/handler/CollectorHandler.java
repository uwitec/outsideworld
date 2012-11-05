package com.processor.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.processor.Context;
import com.processor.ProcessorEngine;
import com.processor.model.CollectorType;

public class CollectorHandler extends AbstractHandler {

	private static final String COLLECTION = "COLLECTION";

	private CollectorType colletor = null;

	public CollectorHandler(CollectorType collector) {
		this.colletor = collector;
	}

	@Override
	public void process(Context context) {
		// parameters
		Map<String, Object> params = getParameters(context, colletor.getParam());

		// collection
		String name = colletor.getName();
		Set<String> keys = context.getCollector(name);
		Map<String, Object> collection = new HashMap<String, Object>();
		for (String key : keys) {
			collection.put(key, context.getParam(key));
		}
		params.put(COLLECTION, collection);

		// handler
		Handler handler = ProcessorEngine.getHandler(colletor.getHandler());
		Object result = handler.handle(params);
		context.addElement(colletor.getName(), result);
	}

}

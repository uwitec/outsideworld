package com.processor.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.processor.Context;
import com.processor.ProcessorEngine;
import com.processor.handler.api.Handler;
import com.processor.model.CollectorType;

public class CollectorHandler extends BaseHandler {

	private CollectorType colletorType = null;

	public CollectorHandler(CollectorType collectorType) {
		this.colletorType = collectorType;
	}

	public void collect(Context context) throws Exception {

	}

	public void initialize(Context context) {
		// parameters
		Map<String, Object> params = getParameters(context,
				colletorType.getParam());

		// collection
		String name = colletorType.getName();
		Set<String> keys = context.getCollector(name);
		Map<String, Object> collection = new HashMap<String, Object>();
		for (String key : keys) {
			collection.put(key, context.getParam(key));
		}

		// handler
		Handler handler = ProcessorEngine.getHandler(colletorType.getHandler());
		Object result = handler.handle(params);
		context.addElement(colletorType.getName(), result);
	}

	@Override
	public String getName() {
		return colletorType.getName();
	}

}

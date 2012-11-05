package com.processor.handler;

import java.util.Map;

import com.processor.Context;
import com.processor.ProcessorEngine;
import com.processor.model.ElementType;

public class ElementHandler extends AbstractHandler {

	private ElementType element = null;

	public ElementHandler(ElementType element) {
		this.element = element;
	}

	@Override
	public void process(Context context) {
		Map<String, Object> params = getParameters(context, element.getParam());
		Handler handler = ProcessorEngine.getHandler(element.getHandler());
		Object result = handler.handle(params);
		context.addElement(element.getName(), result);
		if (element.getCollector() != null) {
			for (String collector : element.getCollector().split(",")) {
				context.addCollector(collector, element.getName());
			}
		}
	}

}

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
		if (element.getOutput()) {
			context.addOutput(element.getName(), result);
		} else {
			context.addElement(element.getName(), result);
		}
	}

}

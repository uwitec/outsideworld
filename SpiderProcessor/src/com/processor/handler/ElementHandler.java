package com.processor.handler;

import java.util.Map;

import com.processor.Context;
import com.processor.handler.impl.TestHandler;
import com.processor.model.ElementType;

public class ElementHandler extends Handler {

	private ElementType element = null;

	public ElementHandler(ElementType element) {
		this.element = element;
	}

	@Override
	public void process(Context context) {
		Map<String, Object> params = getParameters(context, element.getParam());
		Object result = new TestHandler().process(params);
		if (element.getOutput()) {
			context.addOutput(element.getName(), result);
		} else {
			context.addElement(element.getName(), result);
		}
	}

}

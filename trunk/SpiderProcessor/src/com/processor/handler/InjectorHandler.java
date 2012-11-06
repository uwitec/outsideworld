package com.processor.handler;

import java.util.Map;

import com.processor.Context;
import com.processor.ProcessorEngine;
import com.processor.model.InjectorType;

public class InjectorHandler extends AbstractHandler {

	private InjectorType injector;
	private Injector injectorHandler;

	public InjectorHandler(InjectorType injector) {
		this.injector = injector;

	}

	public Map<String, Object> next() {
		return injectorHandler.next();
	}

	@Override
	public void process(Context context) {
		Map<String, Object> params = getParameters(context, injector.getParam());
		injectorHandler = (Injector) ProcessorEngine.getHandler(injector
				.toString());
		injectorHandler.initialize(params);
	}
}

package com.processor.handler;

import java.util.Map;

import com.processor.Context;
import com.processor.ProcessorEngine;
import com.processor.handler.api.Injector;
import com.processor.model.InjectorType;

public class InjectorHandler extends BaseHandler {

	private InjectorType injectorType;
	private Injector injectorHandler;

	public InjectorHandler(InjectorType injectorType) {
		this.injectorType = injectorType;
	}

	public Map<String, Object> next() throws Exception {
		return injectorHandler.getNext();
	}

	public void initialize(Context context) throws Exception {
		Map<String, Object> params = getParameters(context,
				injectorType.getParam());
		injectorHandler = ProcessorEngine
				.getInjector(injectorType.getHandler());
		injectorHandler.initialize(params);
	}

	@Override
	public String getName() {
		return injectorType.getName();
	}
}

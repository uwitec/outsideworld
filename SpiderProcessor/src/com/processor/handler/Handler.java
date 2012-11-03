package com.processor.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.processor.Context;
import com.processor.model.ParamType;

public abstract class Handler {

	public abstract void process(Context context);

	protected Map<String, Object> getParameters(Context context,
			List<ParamType> params) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (ParamType param : params) {
			String name = param.getName();
			Object value = null;
			if (param.getRef() == null) {
				value = param.getValue();
			} else {
				value = context.getParam(param.getRef());
			}
			parameters.put(name, value);
		}
		return parameters;
	}
}

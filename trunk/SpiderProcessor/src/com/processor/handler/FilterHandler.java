package com.processor.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.processor.Context;
import com.processor.ProcessorEngine;
import com.processor.model.ElementType;
import com.processor.model.FilterType;

public class FilterHandler extends AbstractHandler {

	private FilterType filter = null;

	private List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();

	public FilterHandler(FilterType filter) {
		this.filter = filter;

		List<FilterType> filterList = filter.getFilter();
		for (FilterType subFilter : filterList) {
			handlers.add(new FilterHandler(subFilter));
		}

		List<ElementType> elementList = filter.getElement();
		for (ElementType element : elementList) {
			handlers.add(new ElementHandler(element));
		}
	}

	@Override
	public void process(Context context) {
		Map<String, Object> params = getParameters(context, filter.getParam());
		Handler filterHandler = ProcessorEngine.getHandler(filter
				.getHandler());
		Object result = filterHandler.handle(params);

		// filter
		boolean flag = false;
		if (result != null && result instanceof Boolean) {
			if (Boolean.TRUE.equals((Boolean) result)) {
				flag = true;
			}
		} else if (result != null && result instanceof Integer) {
			if ((Integer) result > 0) {
				flag = true;
			}
		} else if (result != null && result instanceof String) {
			if (!"".equals(result.toString().trim())) {
				flag = true;
			}
		} else if (result != null && result instanceof Collection) {
			if (((Collection<?>) result).size() > 0) {
				flag = true;
			}
		} else if (result != null && result instanceof Map) {
			if (((Map<?, ?>) result).size() > 0) {
				flag = true;
			}
		} else if (result != null) {
			flag = true;
		}

		// following handlers
		if (flag) {
			for (AbstractHandler handler : handlers) {
				handler.process(context);
			}
		}
	}

}

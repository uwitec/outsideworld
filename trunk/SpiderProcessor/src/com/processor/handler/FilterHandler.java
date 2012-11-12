package com.processor.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.processor.Context;
import com.processor.ProcessorEngine;
import com.processor.handler.api.Handler;
import com.processor.model.ElementType;
import com.processor.model.FilterType;

public class FilterHandler extends AbstractHandler {

	private FilterType filterType = null;
	private List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();

	public FilterHandler(FilterType filterType) {
		this.filterType = filterType;

		List<FilterType> filterList = filterType.getFilter();
		for (FilterType subFilter : filterList) {
			handlers.add(new FilterHandler(subFilter));
		}

		List<ElementType> elementList = filterType.getElement();
		for (ElementType element : elementList) {
			handlers.add(new ElementHandler(element));
		}
	}

	@Override
	public void process(Context context) throws Exception {
		// get filter
		Map<String, Object> params = getParameters(context,
				filterType.getParam());
		Handler filterHandler = ProcessorEngine.getHandler(filterType
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
				try {
					handler.process(context);
				} catch (Exception e) {
					context.error(handler, e);
					if (context.isFailsafe()) {
						continue;
					} else {
						break;
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		return filterType.getName();
	}

}

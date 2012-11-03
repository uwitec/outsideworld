package com.processor.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.processor.Context;
import com.processor.handler.impl.TestFilter;
import com.processor.model.ElementType;
import com.processor.model.FilterType;

public class FilterHandler extends Handler {

	private FilterType filter = null;

	private List<Handler> handlers = new ArrayList<Handler>();

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
		boolean result = new TestFilter().process(params);
		if (result) {
			for (Handler handler : handlers) {
				handler.process(context);
			}
		}
	}

}

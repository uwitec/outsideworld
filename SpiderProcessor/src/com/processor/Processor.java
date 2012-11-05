package com.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.processor.handler.ElementHandler;
import com.processor.handler.FilterHandler;
import com.processor.handler.AbstractHandler;
import com.processor.model.ElementType;
import com.processor.model.FilterType;
import com.processor.model.ProcessorType;

public class Processor implements Runnable {

	private List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();
	private Map<String, String> params = new HashMap<String, String>();
	private Context context = null;

	public Processor(ProcessorType processor) {
		List<FilterType> filterList = processor.getFilter();
		for (FilterType filter : filterList) {
			handlers.add(new FilterHandler(filter));
		}

		List<ElementType> elementList = processor.getElement();
		for (ElementType element : elementList) {
			handlers.add(new ElementHandler(element));
		}
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void setData(Map<String, Object> data) {
		this.context = new Context(data);
		for (String key : params.keySet()) {
			context.addElement(key, params.get(key));
		}
	}

	@Override
	public void run() {
		for (AbstractHandler handler : handlers) {
			handler.process(context);
		}
	}
}

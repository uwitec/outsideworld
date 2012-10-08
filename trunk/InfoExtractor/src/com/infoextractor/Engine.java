package com.infoextractor;

import java.util.List;
import java.util.Map;

public class Engine {

	private List<Component> list;
	private Map<String, Object> context;
	private Map<String, Object> result;

	public Map<String, Object> execute() {
		for (Component component : list) {
			if (component instanceof Variable) {
				// get parameters
				Object var = ((Variable) component).execute(context);
			} else if (component instanceof Element) {
				Object value = ((Element) component).execute(context);
			}
		}
		return null;
	}

}

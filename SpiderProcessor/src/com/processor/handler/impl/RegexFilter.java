package com.processor.handler.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.processor.handler.api.Handler;

public class RegexFilter implements Handler {

	private static final String REGEX = "regex";
	private static final String TEXT = "text";

	public Object handle(Map<String, Object> params) {
		// check parameters
		if (!params.containsKey(REGEX) || !params.containsKey(TEXT)) {
			return null;
		}

		Pattern pattern = Pattern.compile(params.get(REGEX).toString());
		String text = params.get(TEXT).toString();
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
}

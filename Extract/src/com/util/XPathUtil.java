package com.util;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.TagNode;

public class XPathUtil {

	public static String getResult(TagNode node, String xpath) throws Exception {
		String result = "";
		Object[] objs = node.evaluateXPath(xpath);
		if (objs != null && objs.length > 0) {
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] instanceof TagNode) {
					result += extractTxt((TagNode) objs[i]);
					if (!result.trim().isEmpty()) {
						return result;
					}
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static String extractTxt(TagNode node) {
		List<TagNode> children = node.getAllElementsList(true);
		for (TagNode child : children) {
			if (child.getName().equalsIgnoreCase("script")) {
				child.removeAllChildren();
				node.removeChild(child);
			}
		}
		return node.getText().toString();
	}

	public static List<String> getResults(TagNode node, String xpath)
			throws Exception {
		List<String> result = new ArrayList<String>();
		Object[] nodes = node.evaluateXPath(xpath);
		if (nodes != null && nodes.length > 0) {
			for (Object n : nodes) {
				if (n instanceof TagNode) {
					result.add(((TagNode) n).getText().toString());
				} else {
					result.add(n.toString());
				}
			}
		}
		return result;
	}
}

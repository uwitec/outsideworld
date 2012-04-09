package com.util;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class XPathUtil {
	private static HtmlCleaner htmlCleaner = new HtmlCleaner();

	public static String getResult(TagNode node, String xpath) throws Exception {
		Object[] objs = node.evaluateXPath(xpath);
		if (objs != null && objs.length > 0) {
			if (objs[0] instanceof TagNode) {
				return extractTxt((TagNode) objs[0]);
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	public static String extractTxt(TagNode node) {
		String text = "";
		List<TagNode> children = node.getAllElementsList(true);
		for (TagNode child : children) {
			if (child.getName().equalsIgnoreCase("script")) {
				child.removeAllChildren();
				node.removeChild(child);
			}
		}
		System.out.println(node.getText());
		return node.getText().toString();
	}

	public static List<String> getResults(TagNode node, String xpath)
			throws Exception {
		List<String> result = new ArrayList<String>();
		TagNode[] nodes = (TagNode[]) node.evaluateXPath(xpath);
		if (nodes != null && nodes.length > 0) {
			for (TagNode n : nodes) {
				result.add(n.getText().toString());
			}
		}
		return result;
	}
}

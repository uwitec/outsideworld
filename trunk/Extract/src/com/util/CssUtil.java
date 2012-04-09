package com.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CssUtil {

	public static String getResult(Document doc, String selector)
			throws Exception {
		Elements elements = doc.select(selector);
		Iterator<Element> iterator = elements.iterator();
		if (iterator.hasNext()) {
			return iterator.next().text();
		}
		return "";
	}

	public static List<String> getResults(Document doc, String selector)
			throws Exception {
		List<String> result = new ArrayList<String>();
		Elements elements = doc.select(selector);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next().text());
		}
		return result;
	}

	public static List<String> getResults(Document doc, String selector,
			String attr) throws Exception {
		List<String> result = new ArrayList<String>();
		Elements elements = doc.select(selector);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next().attr(attr));
		}
		return result;
	}

	public static String getHtmlResult(Document doc, String selector)
			throws Exception {
		Elements elements = doc.select(selector);
		Iterator<Element> iterator = elements.iterator();
		if (iterator.hasNext()) {
			return iterator.next().html();
		}
		return "";
	}

	public static List<String> getHtmlResults(Document doc, String selector)
			throws Exception {
		List<String> result = new ArrayList<String>();
		Elements elements = doc.select(selector);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next().html());
		}
		return result;
	}
}

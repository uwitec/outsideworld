package com.model;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParsedHtml {

	private static HtmlCleaner htmlCleaner = new HtmlCleaner();

	private Document doc;

	private TagNode node;

	public ParsedHtml(String html) {
		doc = Jsoup.parse(html);
		node = htmlCleaner.clean(html);
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public TagNode getNode() {
		return node;
	}

	public void setNode(TagNode node) {
		this.node = node;
	}
}

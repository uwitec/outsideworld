package com.model;

import java.net.URL;

import org.htmlcleaner.TagNode;

public class Page {

	private URL url;

	private int depth = 0;

	private String html;

	private TagNode doc;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public TagNode getDoc() {
		return doc;
	}

	public void setDoc(TagNode doc) {
		this.doc = doc;
	}
}

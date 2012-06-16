package com.model;

import java.net.URL;

import org.htmlcleaner.TagNode;

import com.entity.Source;

public class Page {

	private URL url;
	
	private Source source;

	private int depth = 0;

	private String html;

	private TagNode doc;
	/**
	 * 表明当前的是什么类型，是抽取页面还是目录页面0表示目录页面
	 */
	private int type = 0;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}
}

package com.model;

import java.net.URL;

import org.htmlcleaner.TagNode;

public class Page {

	private URL url;

	private int depth = 0;

	private String html;

	private TagNode doc;
	//表面当前站点下载页面的类型，比如图片(img),(flash)等等
	private String format;
    //表明当前站点所属的频道,比如素材(sucai)
	private String channel; 
	
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {
	/**
	 * 表示url
	 */
	private String url;
	/**
	 * 页面的内容
	 */
	private byte[] rawData;
	/**
	 * 解码后的string
	 */
	private String pageString;
	/**
	 * 编码方式
	 */
	private String encoding;
	/**
	 * 表示抓取过程中的状态，如果出错，则状态设置为false
	 */
	private boolean status = true;
	/**
	 * 表示抓取的内容
	 */
    private String content;
    /**
     * 表示抓取的标题
     */
    private String title;
    /**
     * 表示发布时间
     */
    private Date pubTime;
    /**
     * 抓取时间
     */
    private Date crawlTime;
    /**
     * 表示回帖数
     */
    private int replyNum;
    /**
     * 转发数
     */
    private int transNum;
    /**
     * 抓取的类型 “0”表示论坛 “1”表示新闻 “2”表示博客
     */
    private String type;
    /**
     * 连接
     */
    private List<OUrl> ourls = new ArrayList<OUrl>();
    /**
     * 来源
     */
    private String source;
    /**
     * 模板设置
     */
    private Template template;
    
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}
	public Date getCrawlTime() {
		return crawlTime;
	}
	public void setCrawlTime(Date crawlTime) {
		this.crawlTime = crawlTime;
	}
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getTransNum() {
		return transNum;
	}
	public void setTransNum(int transNum) {
		this.transNum = transNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<OUrl> getOurls() {
		return ourls;
	}
	public void setOurls(List<OUrl> ourls) {
		this.ourls = ourls;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public byte[] getRawData() {
		return rawData;
	}
	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public String getPageString() {
		return pageString;
	}
	public void setPageString(String pageString) {
		this.pageString = pageString;
	}
}

package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {
	/**
	 * ��ʾurl
	 */
	private String url;
	/**
	 * ҳ�������
	 */
	private byte[] rawData;
	/**
	 * ������string
	 */
	private String pageString;
	/**
	 * ���뷽ʽ
	 */
	private String encoding;
	/**
	 * ��ʾץȡ�����е�״̬�����������״̬����Ϊfalse
	 */
	private boolean status = true;
	/**
	 * ��ʾץȡ������
	 */
    private String content;
    /**
     * ��ʾץȡ�ı���
     */
    private String title;
    /**
     * ��ʾ����ʱ��
     */
    private Date pubTime;
    /**
     * ץȡʱ��
     */
    private Date crawlTime;
    /**
     * ��ʾ������
     */
    private int replyNum;
    /**
     * ת����
     */
    private int transNum;
    /**
     * ץȡ������ ��0����ʾ��̳ ��1����ʾ���� ��2����ʾ����
     */
    private String type;
    /**
     * ����
     */
    private List<OUrl> ourls = new ArrayList<OUrl>();
    /**
     * ��Դ
     */
    private String source;
    /**
     * ģ������
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

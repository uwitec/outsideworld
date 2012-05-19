package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.model.policy.Template;

public class Item {
	/**
	 * 此item的num
	 */
	private long num;
	/**
	 * 当前url
	 */
	private String url;
	/**
	 * byte内容
	 */
	private byte[] rawData;
	/**
	 * page的string格式，是由rawData和encoding获得的
	 */
	private String pageString;
	/**
	 * 编码
	 */
	private String encoding;
	/**
	 * 解析时候的状态，在解析过程中，一旦发觉状态有错，则立刻停止解析
	 */
	private boolean status = true;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 发布时间
	 */
	private Date pubTime;
	/**
	 * 抓取时间
	 */
	private Date crawlTime;
	/**
	 * ��ʾ������
	 */
	private int replyNum;
	/**
	 * 转贴数目
	 */
	private int transNum;
	/**
	 * 连接所属类型“0”表示新闻 “1”表示BBS “2”表示微薄
	 */
	private String type;
	/**
	 * 外部urls
	 */
	private List<OUrl> ourls = new ArrayList<OUrl>();
	/**
	 * 发布者
	 */
	private String source;
	/**
	 * 模板
	 */
	private Template template;
	/**
	 * 记录此item属于哪个topic
	 */
	private String topicIds = "";

	private String metaTitle;

	private ParsedHtml parsedHtml;

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

	public ParsedHtml getParsedHtml() {
		return parsedHtml;
	}

	public void setParsedHtml(ParsedHtml parsedHtml) {
		this.parsedHtml = parsedHtml;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public String getTopicIds() {
		return topicIds;
	}

	public void setTopicIds(String topicIds) {
		this.topicIds = topicIds;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}
}

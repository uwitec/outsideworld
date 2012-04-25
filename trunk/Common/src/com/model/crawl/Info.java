package com.model.crawl;

import java.util.Date;

/*
 * 舆情信息表
 */
public class Info {

	/* ID */
	private String id;

	/* 标题 */
	private String title;

	/* 摘要 */
	private String summary;

	/* URL */
	private String url;

	/* 舆情类型 */
	private String type;

	/* 热度 ，例如：BBS-浏览/回复，微博-转发/评论 */
	private int hotVal1;

	/* 热度 */
	private int hotVal2;

	/* 热度 */
	private int hotVal3;

	/* 抓取时间 */
	private Date fetchDTTM;

	/* 合并后的UUID,类似舆情信息的sortedId相同 */
	private String sortedId;

	/* 主题ID */
	private String topicId;

	/* 舆情类型 */
	public static enum InfoType {
		WEB, BBS, BLOG, SEARCH, WEIBO, QQ
	}
}

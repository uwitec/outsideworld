package com.model.policy;

public class Source {

	/* ID */
	private int id;

	/* 分类ID */
	private int categoryId;

	/* 名称 */
	private String name;

	/* URL */
	private String url;

	/* 类型 */
	private SourceType type;

	public static enum SourceType {
		WEBSITE, WEIBO, SEARCH, QQ
	}
}

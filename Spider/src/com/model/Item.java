package com.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 爬虫类
 * <p>
 * 类说明
 * </p>
 * <p>
 * Copyright: 版权所有 (c) 2010 - 2030
 * </p>
 * <p>
 * Company: Travelsky
 * </p>
 * 
 * @author fangxia722
 * @version 1.0
 * @since Jun 11, 2012
 */
public class Item {
	/**
	 * 表明当前Item是何种类型
	 */
	private String type;
	/**
	 * 表明当前Item的url是什么
	 */
	private String url;
	/**
	 * 记录抓取出来的内容
	 */
	private Map<String, String> fields = new HashMap<String, String>();
	/**
	 * 表示抓取的时间
	 */
	private Date date;

	public Set<Entry<String, String>> fieldSet() {
		return fields.entrySet();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getField(String key) {
		return fields.get(key);
	}

	public void addField(String key, String value) {
		fields.put(key, value);
	}

	public Date getDate() {
		if (date == null) {
			date = new Date();
		}
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isNoField() {
		if (fields.size() == 0) {
			return true;
		}
		return false;
	}
}

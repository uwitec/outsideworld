package com.pss.web.vo;

import java.util.List;

/**
 * 
 * @author Aries Zhao
 * 
 * @param <T>
 */
public class Pagination<T> {
	private int page = 1;
	private int pageSize = 20;
	private int totalCount;
	private List<T> items;

	public Pagination(List<T> items, int page, int totalCount) {
		this.items = items;
		this.page = page;
		this.totalCount = totalCount;
	}

	public Pagination(List<T> items, int page, int totalCount, int pageSize) {
		this.items = items;
		this.page = page;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
	}

	public List<T> getItems() {
		return items;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPage() {
		return page;
	}

	public int getTotalPage() {
		return (totalCount + pageSize - 1) / pageSize;
	}

	public int getNextPage() {
		if (page < getTotalPage()) {
			return page + 1;
		} else {
			return getTotalPage();
		}
	}

	public int getPrePage() {
		if (page > 1) {
			return page - 1;
		} else {
			return 1;
		}
	}
}

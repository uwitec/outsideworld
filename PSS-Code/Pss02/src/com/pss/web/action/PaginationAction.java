package com.pss.web.action;

import java.util.List;

/**
 * 分页Action
 * 
 * @author Aries Zhao
 * 
 * @param <T>
 */
public abstract class PaginationAction<T> extends AbstractAction {

	private static final long serialVersionUID = 1L;

	protected int page = 1;
	protected int pageSize = 5;
	protected int totalCount;
	protected List<T> items;

	public List<T> getItems() {
		return items;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 当前页码
	 * 
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 总行数
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return (totalCount + pageSize - 1) / pageSize;
	}

	/**
	 * 后一页页码
	 * 
	 * @return
	 */
	public int getNextPage() {
		if (page < getTotalPage()) {
			return page + 1;
		} else {
			return getTotalPage();
		}
	}

	/**
	 * 前一页页码
	 * 
	 * @return
	 */
	public int getPrePage() {
		if (page > 1) {
			return page - 1;
		} else {
			return 1;
		}
	}

	/**
	 * 每页显示的行数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 当前页总行数
	 * 
	 * @return
	 */
	public int getItemSize() {
		if (items == null) {
			return 0;
		} else {
			return items.size();
		}
	}

	/**
	 * 起始行数
	 * 
	 * @return
	 */
	public int getFirst() {
		return page * pageSize - pageSize + 1;
	}

	/**
	 * 末尾行数
	 * 
	 * @return
	 */
	public int getLast() {
		return page * pageSize;
	}

	/**
	 * 起始记录数（数据库）
	 * 
	 * @return
	 */
	protected int getOffset() {
		return page * pageSize - pageSize;
	}
}

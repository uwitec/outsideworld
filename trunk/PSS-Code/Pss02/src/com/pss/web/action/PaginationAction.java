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
	protected String selectedIds;

	/**
	 * 起始记录数（数据库）
	 * 
	 * @return
	 */
	protected int getOffset() {
		return page * pageSize - pageSize;
	}

	/**
	 * 设置页码
	 * 
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 设置每页行数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 设置当前选择的ID
	 * 
	 * @param selectedIds
	 */
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	/**
	 * 当前页的记录
	 * 
	 * @return
	 */
	public List<T> getItems() {
		return items;
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
	 * 总页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return (totalCount + pageSize - 1) / pageSize;
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
	 * 初始页面
	 * 
	 * @return
	 */
	abstract public String home();

	/**
	 * 新建页面
	 * 
	 * @return
	 */
	abstract public String add();

	/**
	 * 更新页面
	 * 
	 * @return
	 */
	abstract public String update();

	/**
	 * 删除页面
	 * 
	 * @return
	 */
	abstract public String delete();

	/**
	 * 新建实体
	 * 
	 * @return
	 */
	abstract public String addEntity();

	/**
	 * 更新实体
	 * 
	 * @return
	 */
	abstract public String updateEntity();

	/**
	 * 删除实体
	 * 
	 * @return
	 */
	abstract public String deleteEntity();

	/**
	 * 查询实体列表
	 * 
	 * @return
	 */
	abstract public String queryEntity();
}

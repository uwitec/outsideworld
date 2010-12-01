package com.jeecms.core.entity;

import java.util.Set;

import com.jeecms.common.util.SelectTree;
import com.jeecms.core.entity.base.BaseFunction;
import com.jeecms.core.util.PriorityInterface;

public class Function extends BaseFunction implements SelectTree,
		PriorityInterface {
	private static final long serialVersionUID = 1L;

	/**
	 * 功能集的分隔符
	 */
	public static final String FUNC_SPLIT = "@";
	/**
	 * 下拉列表树
	 */
	private String selectTree;

	public String getSelectTree() {
		return selectTree;
	}

	public String getTreeName() {
		return getName();
	}

	public Function getTreeParent() {
		return getParent();
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Function() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Function(java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Function(java.lang.Long id, java.lang.Integer priority,
			java.lang.Boolean menu) {

		super(id, priority, menu);
	}

	/* [CONSTRUCTOR MARKER END] */

	public void setSelectTree(String selectTree) {
		this.selectTree = selectTree;
	}

	public Set<? extends SelectTree> getTreeChild() {
		return getChild();
	}

	@SuppressWarnings("unchecked")
	public void setTreeChild(Set treeChild) {
		// do nothing
	}

	public Set<? extends SelectTree> getTreeChildRaw() {
		return null;
	}
}
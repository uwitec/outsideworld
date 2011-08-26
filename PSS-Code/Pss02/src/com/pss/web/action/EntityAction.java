/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.web.action.EntityAction.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Aug 26, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.web.action;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.pss.domain.model.entity.Entity;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.exception.EntityNotExistedException;
import com.pss.service.IBusinessService;
import com.pss.web.util.WebUtil;

/**
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
 * @author Travelsky
 * @version 1.0
 * @since Aug 26, 2011
 */
public abstract class EntityAction<T extends Entity> extends AbstractAction {

	private static final long serialVersionUID = 1L;

	// 4、增删改查的抽象方法(查询的返回结果)
	protected List<T> items;
	// 5、保存实体对象
	protected T entity;
	// 6、选中的Id
	protected String selectedIds;

	// 构造方法，在构造方法中，初始化Entity的对象
	@SuppressWarnings("unchecked")
	public EntityAction() {
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		try {
			entity = entityClass.newInstance();
		} catch (InstantiationException e) {
			// Ignore
		} catch (IllegalAccessException e) {
			// Ignore
		}
	}

	// 获得service的对象
	public abstract IBusinessService<T> service();

	/**
	 * 设置当前实体
	 * 
	 * @param entity
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

	/**
	 * 返回当前实体
	 * 
	 * @return
	 */
	@JSON()
	public T getEntity() {
		return entity;
	}

	/**
	 * 初始页面
	 * 
	 * @return
	 */
	public String home() {
		return SUCCESS;
	}

	/**
	 * 新建页面
	 * 
	 * @return
	 */
	public String add() {
		return SUCCESS;
	}

	/**
	 * 更新页面
	 * 
	 * @return
	 */
	public String update() {
		try {
			entity = service().find(entity.getId());
		} catch (BusinessHandleException e) {
			addActionError("数据已经被删除");
		}
		return SUCCESS;
	}

	/**
	 * 删除页面
	 * 
	 * @return
	 */
	public String delete() {
		return SUCCESS;
	}

	/**
	 * 新建实体
	 * 
	 * @return
	 */
	public String addEntity() {
		try {
			entity.setTenant(getTenantId());
			service().add(entity);
			setCorrect(true);
		} catch (BusinessHandleException e) {
			setCorrect(false);
		} catch (EntityAlreadyExistedException e) {
			setCorrect(false);
		}
		return SUCCESS;
	}

	/**
	 * 更新实体
	 * 
	 * @return
	 */
	public String updateEntity() {
		try {
			service().update(entity);
			setCorrect(true);
		} catch (BusinessHandleException e) {
			setCorrect(false);
			addActionError(e.getMessage());
		} catch (EntityNotExistedException e) {
			setCorrect(false);
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 删除实体
	 * 
	 * @return
	 */
	public String deleteEntity() {
		try {
			service().delete(WebUtil.split(selectedIds, ","));
		} catch (BusinessHandleException e) {
			setCorrect(false);
			return ERROR;
		}
		setCorrect(true);
		return SUCCESS;
	}

	/**
	 * 查询实体列表
	 * 
	 * @return
	 */
	abstract public String queryEntity();

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
	@JSON()
	public List<T> getItems() {
		return items;
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
}

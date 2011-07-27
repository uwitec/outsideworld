package com.pss.web.action.system;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.sys.Role;
import com.pss.domain.model.entity.sys.User;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IUserService;
import com.pss.web.action.AbstractAction;

public class UserAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;

	private List<User> userList;

	private User user;
	// 需要删除的用户id列表
	private List<String> deleteIds;

	public String home() {
		return SUCCESS;
	}

	/**
	 * 初始化查询界面，显示空列表和查询条件
	 * 
	 * @return
	 */
	public String initQuery() {
		try {
			userList = userService.allUsers(getTenantId());
		} catch (BusinessHandleException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * 用户查询(查询条件主要为用户名和角色名称，其中角色是可以下拉选择的，用户名称可以支持模糊匹配)
	 */
	public String query() {
		// 获得当前登陆用户的tanentId
		String tanentId = getTenantId();
		if (StringUtils.isBlank(tanentId)) {
			return INPUT;
		}
		try {
			userList = userService.queryUsers(user);
		} catch (BusinessHandleException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 用户删除
	 * 
	 * @return
	 */
	public String delete() {
		try {
			userService.delete(deleteIds);
		} catch (BusinessHandleException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * 新建用户
	 */
	public String add() {
		return SUCCESS;
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 */
	public String update() {
		return SUCCESS;
	}

	/*
	 * 添加用户
	 */
	public String addUser() {
		try {
			user.setTenant(getTenantId());
			Role role = new Role();
			role.setRoleId("1");
			user.setRole(role);
			userService.save(user, true);
			setCorrect(true);
		} catch (BusinessHandleException e) {
			setCorrect(false);
		}
		return SUCCESS;
	}

	/**
	 * 保存用户
	 * 
	 * @return
	 */
	public String save() {
		String result = "";
		// 表示新增流程
		if (StringUtils.equals("true", getIsNew())) {
			user.setTenant(getTenantId());
			user.setStatus("0");
			try {
				result = userService.save(user, true);
			} catch (BusinessHandleException e) {
				return SUCCESS;
			}
		}
		// 表示修改流程
		else {
			try {
				result = userService.save(user, true);
			} catch (BusinessHandleException e) {
				return ERROR;
			}
		}
		if (StringUtils.equals(result, "")) {
			return SUCCESS;
		} else {
			addActionError(getText(result));
			return INPUT;
		}
	}

	@JSON(name = "items")
	public List<User> getUserList() {
		return userList;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}

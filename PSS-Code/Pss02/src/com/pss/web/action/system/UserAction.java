package com.pss.web.action.system;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.sys.User;
import com.pss.service.IUserService;
import com.pss.web.action.AbstractAction;

public class UserAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;

	private List<User> userList;

	private User user;

	/*
	 * 用户查询
	 */
	public String query() {
		return SUCCESS;
	}

	/*
	 * 新建用户
	 */
	public String add() {
		return SUCCESS;
	}

	/*
	 * 添加用户
	 */
	public String addUser() {
		System.out.println(user);
		return SUCCESS;
	}

	@JSON
	public List<User> getUserList() {
		return userList;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

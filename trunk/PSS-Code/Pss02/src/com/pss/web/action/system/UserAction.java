package com.pss.web.action.system;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.sys.User;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IUserService;
import com.pss.web.action.PaginationAction;
import com.pss.web.util.WebUtil;

/**
 * 
 * @author Aries Zhao
 * 
 */
public class UserAction extends PaginationAction<User> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;

	private User user;

	@Override
	public String home() {
		return SUCCESS;
	}

	@Override
	public String add() {
		return SUCCESS;
	}

	@Override
	public String update() {
		try {
			List<User> result = userService.queryUsers(user);
			if (result != null && result.size() > 0) {
				user = result.get(0);
				return SUCCESS;
			}
			addActionError("用户已经被删除");
		} catch (BusinessHandleException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String delete() {
		return SUCCESS;
	}

	/**
	 * 初始化查询界面，显示空列表和查询条件
	 * 
	 * @return
	 */
	public String initQuery() {
		try {
			User user = new User();
			user.setTenant(getTenantId());
			totalCount = userService.queryCount(user);

			items = userService.allUsers(getTenantId(), getOffset(), pageSize);
		} catch (BusinessHandleException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public String queryEntity() {
		// 获得当前登陆用户的tanentId
		String tanentId = getTenantId();
		if (StringUtils.isBlank(tanentId)) {
			return INPUT;
		}
		try {
			user.setTenant(getTenantId());
			items = userService.queryUsers(user);
		} catch (BusinessHandleException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String addEntity() {
		try {
			user.setTenant(getTenantId());
			userService.save(user, true);
			setCorrect(true);
		} catch (BusinessHandleException e) {
			setCorrect(false);
		}
		return SUCCESS;
	}

	@Override
	public String deleteEntity() {
		try {
			userService.delete(WebUtil.split(selectedIds, ","));
		} catch (BusinessHandleException e) {
			setCorrect(false);
			return ERROR;
		}
		setCorrect(true);
		return SUCCESS;
	}

	@Override
	public String updateEntity() {
		String result = "";
		try {
			result = userService.save(user, false);
		} catch (BusinessHandleException e) {
			setCorrect(false);
			addActionError(e.getMessage());
			return SUCCESS;
		}
		if (!StringUtils.equals(result, "")) {
			addActionError(result);
			setCorrect(false);
		} else {
			setCorrect(true);
		}
		return SUCCESS;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}

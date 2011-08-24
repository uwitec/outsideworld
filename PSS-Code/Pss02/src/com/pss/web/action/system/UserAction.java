package com.pss.web.action.system;

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
			entity = userService.find(entity.getUserId());
		} catch (BusinessHandleException e) {
			addActionError("用户已经被删除");
		}
		return SUCCESS;
	}

	@Override
	public String delete() {
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
			getQuery().put("userName", entity.getUserName());
			if (entity.getRole() != null)
				getQuery().put("roleId", entity.getRole().getRoleId());

			totalCount = userService.countByParams(getQuery());
			entity.setTenant(getTenantId());

			items = userService.queryByParams(getQuery());
		} catch (BusinessHandleException e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String addEntity() {
		try {
			entity.setTenant(getTenantId());
			userService.save(entity, true);
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
			result = userService.save(entity, false);
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
}

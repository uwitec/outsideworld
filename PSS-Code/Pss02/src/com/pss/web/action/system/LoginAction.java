/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.web.action.system.LoginAction.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jun 19, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.web.action.system;

import org.apache.commons.lang.StringUtils;

import com.pss.common.FieldUtil;
import com.pss.common.annotation.FieldValidation;
import com.pss.domain.model.entity.sys.User;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IUserService;
import com.pss.service.LoginResult;
import com.pss.web.WebKeys;
import com.pss.web.action.AbstractAction;

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
 * @since Jun 19, 2011
 */
public class LoginAction extends AbstractAction {
	private IUserService userService;
	private User user;
	@FieldValidation(isBlank = false, regx = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$")
	private String tenantName;
	@FieldValidation(isBlank = false)
	private String validationCode;

	public String index() {
		return SUCCESS;
	}

	public String login() {
		try {
			LoginResult result = userService.login(user, tenantName);
			if (!StringUtils.isBlank(result.getMessage())) {
				addActionError(getText(result.getMessage()));
				return LOGIN;
			}
			putDataToSession(WebKeys.USER, result.getUser());
		} catch (BusinessHandleException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public IUserService getUserService() {

		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	public boolean validateLogin() {
		if (!FieldUtil.validate(this, "validationCode")
				|| !StringUtils.equalsIgnoreCase(validationCode,
						(String) getDataFromSession(WebKeys.VALIDATION_CODE))) {
			addFieldError("fieldError.validationCode",
					getText("fieldError.validationCode"));
		}
		if (!FieldUtil.validate(this, "tenantName")) {
			addFieldError("fieldError.tenant", getText("fieldError.tenant"));
		}
		if (!FieldUtil.validate(user, "userName")) {
			addFieldError("fieldError.user.userName",
					getText("fieldError.user.userName"));
		}
		if (!FieldUtil.validate(user, "userPassword")) {
			addFieldError("fieldError.user.userPassword",
					getText("fieldError.user.userPassword"));
		}
		if (hasFieldErrors()) {
			return false;
		} else {
			return true;
		}
	}

}
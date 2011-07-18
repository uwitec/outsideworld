package com.pss.web.action.system;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.sys.Tenant;
import com.pss.exception.BusinessHandleException;
import com.pss.service.ITenantService;
import com.pss.web.action.AbstractAction;

public class RegistAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ITenantService tenantService;

	private String account;
	private String password;
	private String passwordAgn;
	private String email;

	/**
	 * 对用户名的校验
	 * 
	 * @return
	 */
	public String validateAccount() {
		try {
			String result = tenantService.vAcount(account);
			if (!StringUtils.isBlank(result)) {
				setFieldError(getText(result));
			}
		} catch (BusinessHandleException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 对email的校验
	 * 
	 * @return
	 */
	public String validateEmail() {
		try {
			String result = tenantService.vEmail(email);
			if (!StringUtils.isBlank(result)) {
				setFieldError(getText(result));
			}
		} catch (BusinessHandleException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 注册
	 * 
	 * @return
	 */
	public String regist() {
		Tenant tenant = new Tenant();
		tenant.setTenantName(account);
		tenant.setTenantPassword(password);
		tenant.setTenantEmail(email);
		try {
			String result = tenantService.regist(tenant);
			if (!StringUtils.isBlank(result)) {
				addActionError(getText(result));
				return INPUT;
			}
			return SUCCESS;
		} catch (BusinessHandleException e) {
			addActionError(getText("exception"));
			return INPUT;
		}
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordAgn(String passwordAgn) {
		this.passwordAgn = passwordAgn;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordAgn() {
		return passwordAgn;
	}

	public String getEmail() {
		return email;
	}
}

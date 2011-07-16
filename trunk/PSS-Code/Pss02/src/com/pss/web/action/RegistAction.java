package com.pss.web.action;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.sys.Tenant;
import com.pss.exception.BusinessHandleException;
import com.pss.service.impl.TenantService;

public class RegistAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TenantService tenantService;

	private String account;
	private String password;
	private String passwordAgn;
	private String email;

	public String validateAccount() {
		try {
			String result = tenantService.vAcount(account);
			if(!StringUtils.isBlank(result)){
				setFieldError(getText(result));
			}
		} catch (BusinessHandleException e) {
			return ERROR;
		}		
		return SUCCESS;
	}
	
	public String validateEmail() {
		try {
			String result = tenantService.vEmail(email);
			if(!StringUtils.isBlank(result)){
				setFieldError(getText(result));
			}
		} catch (BusinessHandleException e) {
			return ERROR;
		}		
		return SUCCESS;
	}

	public String regist() {
		Tenant tenant = new Tenant();
		tenant.setTenantId(new Date().toString());
		tenant.setTenantName(account);
		tenant.setTenantPassword(password);
		tenant.setTenantEmail(email);

		try {
			tenantService.regist(tenant);
			return SUCCESS;
		} catch (BusinessHandleException e) {
			return ERROR;
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

}

package com.pss.web.action;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.pss.domain.model.entity.sys.Tenant;
import com.pss.exception.BusinessHandleException;
import com.pss.service.impl.TenantService;

public class RegistAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TenantService tenantService;

	private String account;
	private String password;
	private String passwordAgn;
	private String email;

	private boolean accountValid = false;
	private String accountMsg = "";

	public String validateAccount() {
		// TODO 验证账号是否重复
		if (account != null && account.contains("test")) {
			accountValid = true;
			accountMsg = "";
		} else {
			accountValid = false;
			accountMsg = "租户账号已经被注册!";
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

	@JSON
	public boolean getAccountValid() {
		return accountValid;
	}

	public String getAccountMsg() {
		return accountMsg;
	}
}

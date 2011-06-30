package com.pss.web.action;

import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;

public class RegistAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String account;

	private boolean valid = false;

	public String validateAccount() {
		// TODO 验证账号是否重复
		if (account != null && account.contains("test")) {
			valid = true;
		}
		return SUCCESS;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@JSON
	public boolean getValid() {
		return valid;
	}
}

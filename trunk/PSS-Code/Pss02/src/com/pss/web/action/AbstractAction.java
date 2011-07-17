package com.pss.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.pss.service.IIdGeneratorService;

public class AbstractAction extends ActionSupport implements SessionAware,
		ServletRequestAware {
	private String fieldError = "";
	private Map<String, Object> session;
	private HttpServletRequest request;
	private IIdGeneratorService idGeneratorService;

	@JSON
	public String getFieldError() {
		return fieldError;
	}

	public void setFieldError(String fieldError) {
		this.fieldError = fieldError;
	}

	@Override
	public void setSession(Map<String, Object> arg) {
		session = arg;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg) {
		request = arg;
	}

	public void putDataToSession(String key, Object value) {
		session.put(key, value);
	}

	public Object getDataFromSession(String key) {
		return session.get(key);
	}

	public IIdGeneratorService getIdGeneratorService() {
		return idGeneratorService;
	}

	public void setIdGeneratorService(IIdGeneratorService idGeneratorService) {
		this.idGeneratorService = idGeneratorService;
	}
	
}

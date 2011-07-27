package com.pss.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.pss.domain.model.entity.sys.User;
import com.pss.web.WebKeys;

public class AbstractAction extends ActionSupport implements SessionAware,
		ServletRequestAware {
	private static final long serialVersionUID = 1L;

	private boolean correct = false;
	private String fieldError = "";
	private Map<String, Object> session;
	private HttpServletRequest request;
	private String isNew;

	@JSON()
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

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public void putDataToSession(String key, Object value) {
		session.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getDataFromSession(String key) {
		Object obj = session.get(key);
		if (obj != null) {
			return (T) obj;
		} else {
			return null;
		}
	}

	protected String getTenantId() {
		User user = getDataFromSession(WebKeys.USER);
		if (user == null) {
			return "";
		} else {
			return user.getTenant();
		}
	}

	@JSON(serialize = true)
	public boolean getCorrect() {
		return correct;
	}

	protected void setCorrect(boolean correct) {
		this.correct = correct;
	}
}

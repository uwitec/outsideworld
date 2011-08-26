package com.pss.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.pss.domain.model.entity.sys.User;
import com.pss.web.WebKeys;

/**
 * 抽象Action，它负责完成以下几项工作 1、对session和servletRequestAware的操作 2、设置请求是否成功的标志
 * 3、判断请求是否是新增 4、增删改查的抽象方法 5、保存实体对象
 * 
 * @author wangzhendong
 * 
 */
public abstract class AbstractAction extends ActionSupport implements
		SessionAware, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	// 1、对session和servletRequestAware的操作
	private Map<String, Object> session;
	@SuppressWarnings("unused")
	private HttpServletRequest request;
	// 2、设置请求是否成功的标志
	private boolean correct = false;
	private String fieldError = "";

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

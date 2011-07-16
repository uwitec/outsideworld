package com.pss.web.action;

import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;

public class AbstractAction extends ActionSupport {
    private String fieldError = "";

    @JSON
	public String getFieldError() {
		return fieldError;
	}

	public void setFieldError(String fieldError) {
		this.fieldError = fieldError;
	}
    
}

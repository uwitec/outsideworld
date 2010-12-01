package com.jeecms.common.struts2.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.TagLibrary;

import com.opensymphony.xwork2.util.ValueStack;

public class FreemarkerTagLibrary implements TagLibrary {
	public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new FreemarkerModels(stack, req, res);
	}

	@SuppressWarnings("unchecked")
	public List<Class> getVelocityDirectiveClasses() {
		throw new RuntimeException(
				"velocity not supported, please select freemarker!");
	}
}

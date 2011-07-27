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

package com.pss.web.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.annotations.JSON;

import com.pss.common.FieldUtil;
import com.pss.common.ValidationImageUtil;
import com.pss.common.ValidationImageUtil.ImageResult;
import com.pss.common.annotation.FieldValidation;
import com.pss.domain.model.entity.sys.User;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IUserService;
import com.pss.service.LoginResult;
import com.pss.web.WebKeys;

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

	private static final long serialVersionUID = 1L;

	private IUserService userService;

	private User user;

	@FieldValidation(isBlank = false, regx = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$")
	private String tenantName;

	@FieldValidation(isBlank = false)
	private String validationCode;

	private ByteArrayInputStream inputStream;

	public String index() {
		return SUCCESS;
	}

	public String login() {
		try {
			LoginResult result = userService.login(user, tenantName);
			if (!StringUtils.isBlank(result.getMessage())) {
				addActionMessage(getText(result.getMessage()));
				setCorrect(false);
			} else {
				putDataToSession(WebKeys.USER, result.getUser());
				setCorrect(true);
			}
		} catch (BusinessHandleException e) {
			addActionError(e.getMessage());
			setCorrect(false);
		}
		return SUCCESS;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	@JSON(serialize = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean validateLogin() {
		if (!FieldUtil.validate(this, "validationCode")
				|| !StringUtils.equalsIgnoreCase(validationCode,
						(String) getDataFromSession(WebKeys.VALIDATION_CODE))) {
			addFieldError("validationCode",
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

	public String validationCode() throws Exception {
		ImageResult result = ValidationImageUtil.createImage(198, 21);
		// 获得字节的输出流
		ByteArrayInputStream input = getByteArrayIpputStream(result.getImage());
		// 将验证码存入session
		putDataToSession(WebKeys.VALIDATION_CODE, result.getResult());
		// 设置输入流，作为返回结果
		inputStream = input;
		return "validationCode";
	}

	private ByteArrayInputStream getByteArrayIpputStream(BufferedImage image)
			throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
		ImageIO.write(image, "JPEG", imageOut);
		imageOut.close();
		ByteArrayInputStream input = new ByteArrayInputStream(
				output.toByteArray());
		output.close();
		return input;
	}

	@JSON(serialize = false)
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
}
/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.domain.model.entity.sys.User.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jun 21, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.domain.model.entity.sys;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.pss.common.annotation.FieldValidation;
import com.pss.domain.model.entity.Entity;
import com.pss.domain.repository.system.UserRepository;
import com.pss.exception.BusinessHandleException;

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
 * @since Jun 21, 2011
 */
public class User extends Entity {
	@FieldValidation(isBlank = false, regx = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$")
	private String userName;
	@FieldValidation(isBlank = false, regx = "^[0-9|a-z|A-Z]{8,16}$")
	private String userPassword;
	private Date lastLoginTime;
	private String status;
	private Role role;

	/**
	 * 更改登录状态
	 * 
	 * @param userRepository
	 */
	public void updateStatus(UserRepository userRepository) {

	}

	/**
	 * 更新上一次登录时间
	 * 
	 * @param userRepository
	 */
	public void updateLastLoginTime(UserRepository userRepository)
			throws BusinessHandleException {
		Date now = new Date(System.currentTimeMillis());
		setLastLoginTime(now);
		userRepository.updateLastLoginTime(this);
	}

	public boolean isRepeateName(UserRepository userRepository)
			throws BusinessHandleException {
		User user = userRepository.find(this);
		if (user != null
				&& (StringUtils.isBlank(getId()) || !StringUtils.equals(getId(),
						user.getId()))) {
			return true;
		}
		return false;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}

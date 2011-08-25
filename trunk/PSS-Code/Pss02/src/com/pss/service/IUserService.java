package com.pss.service;

import com.pss.domain.model.entity.sys.User;
import com.pss.exception.BusinessHandleException;

public interface IUserService extends IBusinessService<User> {

	public LoginResult login(User user, String tenantName)
			throws BusinessHandleException;
}

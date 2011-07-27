/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.service.IUserService.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jun 27, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.service;

import java.util.List;

import com.pss.domain.model.entity.sys.User;
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
 * @since Jun 27, 2011
 */
public interface IUserService {
	public LoginResult login(User user, String tenantName)
			throws BusinessHandleException;

	public List<User> allUsers(String tenant) throws BusinessHandleException;

	/**
	 * 查询user
	 * 
	 * @param user
	 * @return
	 * @throws BusinessHandleException
	 */
	public List<User> queryUsers(User user) throws BusinessHandleException;

	/**
	 * 
	 * @param id
	 * @throws BusinessHandleException
	 */
	public void delete(List<String> id) throws BusinessHandleException;

	/**
	 * 
	 * @param user
	 * @param isNew
	 * @throws BusinessHandleException
	 */
	public String save(User user, boolean isNew) throws BusinessHandleException;
}

/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.domain.repository.system.UserRepository.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jun 27, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.domain.repository.system;

import java.util.List;
import java.util.Map;

import com.pss.dao.system.UserMapper;
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
public class UserRepository {
	private UserMapper userMapper;

	public void add(User user) throws BusinessHandleException {
		userMapper.insert(user);
	}

	public User query(User user) throws BusinessHandleException {
		return userMapper.query(user);
	}

	public User queryById(String userId) throws BusinessHandleException {
		return userMapper.queryById(userId);
	}

	public List<User> queryList(Map<String, Object> params)
			throws BusinessHandleException {
		return userMapper.queryList(params);
	}

	public void update(User user) throws BusinessHandleException {

	}

	public void delete(List<String> ids) throws BusinessHandleException {
		for (String id : ids) {
			userMapper.delete(id);
		}
	}

	public int queryCount(Map<String, Object> params) {
		return userMapper.queryCount(params);
	}

	public void updateLastLoginTime(User user) throws BusinessHandleException {
		try {
			userMapper.updateLastLoginTime(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

}

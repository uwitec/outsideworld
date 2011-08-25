package com.pss.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.sys.User;
import com.pss.domain.repository.system.TenantRepository;
import com.pss.domain.repository.system.UserRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityInvalidateException;
import com.pss.service.IUserService;
import com.pss.service.LoginResult;

public class UserService extends AbstractService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TenantRepository tenantRepository;

	@Transactional
	@Override
	public LoginResult login(User user, String tenantName)
			throws BusinessHandleException {
		LoginResult result = new LoginResult();
		String tenantId = tenantRepository.findIdByName(tenantName);
		if (StringUtils.isBlank(tenantId)) {
			result.setMessage("tenantNotExist");
		} else {
			user.setTenant(tenantId);
			User findUser = userRepository.query(user);
			if (findUser == null) {
				result.setMessage("userNotExist");
			} else {
				findUser.updateStatus(userRepository);
				findUser.updateLastLoginTime(userRepository);
				result.setUser(findUser);
			}
		}
		return result;
	}

	@Override
	public User find(String id) throws BusinessHandleException {
		return userRepository.queryById(id);
	}

	@Override
	public List<User> queryByParams(Map<String, Object> params)
			throws BusinessHandleException {
		return userRepository.queryList(params);
	}

	@Override
	public int countByParams(Map<String, Object> params)
			throws BusinessHandleException {
		try {
			return userRepository.queryCount(params);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Transactional
	@Override
	public void delete(List<String> ids) throws BusinessHandleException {
		userRepository.delete(ids);
	}

	@Transactional
	@Override
	public void save(User user) throws BusinessHandleException,
			EntityInvalidateException {
		if (user.isRepeateName(userRepository)) {
			throw new EntityInvalidateException("user.userName.repeated");
		}
		if (user.getUserId() != null) {
			userRepository.update(user);
		} else {
			user.setUserId(nextStr("user", 64));
			userRepository.add(user);
		}
	}

	@Override
	public void update(User entity) throws BusinessHandleException {
		// TODO Auto-generated method stub

	}

	@Override
	public User queryByEntity(User entity) throws BusinessHandleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countByEntity(User entity) throws BusinessHandleException {
		// TODO Auto-generated method stub
		return 0;
	}
}

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
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.exception.EntityNotExistedException;
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
			User findUser = userRepository.find(user);
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

	@Transactional
	@Override
	public void add(User user) throws BusinessHandleException,
			EntityAlreadyExistedException {
		if (user.isRepeateName(userRepository)) {
			throw new EntityAlreadyExistedException("user.userName.repeated");
		}
		user.setId(nextStr("user", 64));
		userRepository.add(user);
	}

	@Transactional
	@Override
	public void delete(String id) throws BusinessHandleException,
			EntityNotExistedException {
		userRepository.delete(id);
	}

	@Transactional
	@Override
	public void delete(List<String> ids) throws BusinessHandleException {
		for (String id : ids) {
			try {
				userRepository.delete(id);
			} catch (EntityNotExistedException e) {
				throw new BusinessHandleException();
			}
		}
	}

	@Transactional
	@Override
	public void update(User entity) throws BusinessHandleException {
		try {
			userRepository.update(entity);
		} catch (EntityNotExistedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User find(String id) throws BusinessHandleException {
		return userRepository.find(id);
	}

	@Override
	public User find(User entity) throws BusinessHandleException {
		return userRepository.find(entity);
	}

	@Override
	public List<User> query(Map<String, Object> params)
			throws BusinessHandleException {
		return userRepository.query(params);
	}

	@Override
	public int count(Map<String, Object> params) throws BusinessHandleException {
		return userRepository.count(params);
	}

	@Override
	public List<User> query(User entity) throws BusinessHandleException {
		return userRepository.query(entity);
	}

	@Override
	public int count(User entity) throws BusinessHandleException {
		return userRepository.count(entity);
	}
}

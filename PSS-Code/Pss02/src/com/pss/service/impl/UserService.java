package com.pss.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.sys.User;
import com.pss.domain.repository.system.TenantRepository;
import com.pss.domain.repository.system.UserRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IUserService;
import com.pss.service.LoginResult;

public class UserService extends AbstractService implements IUserService {
	private UserRepository userRepository;
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
	public List<User> allUsers(String tenant, int offset, int pageSize) {
		return userRepository.getUsersByTenantId(tenant, offset, pageSize);
	}

	@Override
	public List<User> queryUsers(User user) throws BusinessHandleException {
		return userRepository.queryList(user);
	}

	@Transactional
	@Override
	public void delete(List<String> ids) throws BusinessHandleException {
		userRepository.delete(ids);
	}

	@Transactional
	@Override
	public String save(User user, boolean isNew) throws BusinessHandleException {
		if (user.isRepeateName(userRepository)) {
			return "user.userName.repeated";
		}
		if (!isNew) {
			User oldUser = userRepository.queryById(user.getUserId());
			if (oldUser == null) {
				return "data.deleted";
			}
			List<String> ids = new ArrayList<String>();
			ids.add(oldUser.getUserId());
			userRepository.delete(ids);
			user.setUserId(oldUser.getUserId());
		} else {
			user.setUserId(nextStr("user", 64));
		}
		userRepository.add(user);
		return "";
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public TenantRepository getTenantRepository() {
		return tenantRepository;
	}

	public void setTenantRepository(TenantRepository tenantRepository) {
		this.tenantRepository = tenantRepository;
	}

	@Override
	public int queryCount(User user) throws BusinessHandleException {
		return userRepository.queryCount(user);
	}
}

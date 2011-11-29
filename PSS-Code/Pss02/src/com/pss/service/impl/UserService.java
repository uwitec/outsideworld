package com.pss.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.sys.User;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.system.TenantRepository;
import com.pss.domain.repository.system.UserRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.service.IUserService;
import com.pss.service.LoginResult;

public class UserService extends AbstractService<User> implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TenantRepository tenantRepository;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

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
		String name = StringUtils.lowerCase(user.getClass().getSimpleName());
		user.setId(user.getCode() + sdf.format(new Date())
				+ nextStr(name, 16 - user.getCode().length()));
		userRepository.add(user);
	}

	public BaseRepository<User> repository() {
		return userRepository;
	}
}

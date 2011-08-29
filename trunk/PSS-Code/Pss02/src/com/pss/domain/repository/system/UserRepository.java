package com.pss.domain.repository.system;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.dao.BaseMapper;
import com.pss.dao.system.UserMapper;
import com.pss.domain.model.entity.sys.User;
import com.pss.domain.repository.BaseRepository;
import com.pss.exception.BusinessHandleException;

public class UserRepository extends BaseRepository<User> {

	@Autowired
	private UserMapper userMapper;

	public void updateLastLoginTime(User user) throws BusinessHandleException {
		try {
			userMapper.updateLastLoginTime(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected BaseMapper<User> getMapper() {
		return userMapper;
	}
}

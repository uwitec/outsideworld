package com.pss.domain.repository.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.dao.system.UserMapper;
import com.pss.domain.model.entity.sys.User;
import com.pss.domain.repository.BaseRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.exception.EntityNotExistedException;

public class UserRepository implements BaseRepository<User> {

	@Autowired
	private UserMapper userMapper;

	@Override
	public void add(User user) throws BusinessHandleException,
			EntityAlreadyExistedException {
		userMapper.add(user);
	}

	@Override
	public void delete(String id) throws BusinessHandleException,
			EntityNotExistedException {
		userMapper.delete(id);
	}

	@Override
	public void update(User entity) throws BusinessHandleException,
			EntityNotExistedException {
		userMapper.update(entity);
	}

	@Override
	public User find(String id) throws BusinessHandleException {
		return userMapper.findById(id);
	}

	@Override
	public User find(User entity) throws BusinessHandleException {
		return userMapper.findByEntity(entity);
	}

	@Override
	public List<User> query(User entity) throws BusinessHandleException {
		return userMapper.queryByEntity(entity);
	}

	@Override
	public List<User> query(Map<String, Object> params)
			throws BusinessHandleException {
		return userMapper.queryByParams(params);
	}

	@Override
	public int count(User entity) throws BusinessHandleException {
		return userMapper.countByEntity(entity);
	}

	@Override
	public int count(Map<String, Object> params) throws BusinessHandleException {
		return userMapper.countByParams(params);
	}

	public void updateLastLoginTime(User user) throws BusinessHandleException {
		try {
			userMapper.updateLastLoginTime(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

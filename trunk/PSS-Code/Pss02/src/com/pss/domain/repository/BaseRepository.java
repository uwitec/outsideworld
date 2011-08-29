package com.pss.domain.repository;

import java.util.List;
import java.util.Map;

import com.pss.dao.BaseMapper;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.exception.EntityNotExistedException;

public abstract class BaseRepository<T> {

	abstract protected BaseMapper<T> getMapper();

	public void add(T entity) throws BusinessHandleException,
			EntityAlreadyExistedException {
		getMapper().add(entity);
	}

	public void delete(String id) throws BusinessHandleException,
			EntityNotExistedException {
		getMapper().delete(id);
	}

	public void update(T entity) throws BusinessHandleException,
			EntityNotExistedException {
		getMapper().update(entity);
	}

	public T find(String id) throws BusinessHandleException {
		return getMapper().findById(id);
	}

	public T find(T entity) throws BusinessHandleException {
		return getMapper().findByEntity(entity);
	}

	public List<T> query(T entity) throws BusinessHandleException {
		return getMapper().queryByEntity(entity);
	}

	public List<T> query(Map<String, Object> params)
			throws BusinessHandleException {
		return getMapper().queryByParams(params);
	}

	public int count(T entity) throws BusinessHandleException {
		return getMapper().countByEntity(entity);
	}

	public int count(Map<String, Object> params) throws BusinessHandleException {
		return getMapper().countByParams(params);
	}
}

package com.pss.domain.repository;

import java.util.List;
import java.util.Map;

import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.exception.EntityNotExistedException;

public interface BaseRepository<T> {

	public void add(T entity) throws BusinessHandleException,
			EntityAlreadyExistedException;

	public void delete(String id) throws BusinessHandleException,
			EntityNotExistedException;

	public void update(T entity) throws BusinessHandleException,
			EntityNotExistedException;

	public T find(String id) throws BusinessHandleException;

	public T find(T entity) throws BusinessHandleException;

	public List<T> query(T entity) throws BusinessHandleException;

	public List<T> query(Map<String, Object> params)
			throws BusinessHandleException;

	public int count(T entity) throws BusinessHandleException;

	public int count(Map<String, Object> params) throws BusinessHandleException;
}

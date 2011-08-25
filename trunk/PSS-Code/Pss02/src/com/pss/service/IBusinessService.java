package com.pss.service;

import java.util.List;
import java.util.Map;

import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityInvalidateException;

public interface IBusinessService<T> {

	public void save(T entity) throws BusinessHandleException,
			EntityInvalidateException;

	public void delete(List<String> ids) throws BusinessHandleException;

	public void update(T entity) throws BusinessHandleException;

	public T find(String id) throws BusinessHandleException;

	public T queryByEntity(T entity) throws BusinessHandleException;

	public int countByEntity(T entity) throws BusinessHandleException;

	public List<T> queryByParams(Map<String, Object> params)
			throws BusinessHandleException;

	public int countByParams(Map<String, Object> params)
			throws BusinessHandleException;
}

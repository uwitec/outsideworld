package com.pss.service;

import java.util.List;
import java.util.Map;

import com.pss.exception.BusinessHandleException;

public interface IBusinessService<T> {

	public T find(String id) throws BusinessHandleException;

	public List<T> queryByParams(Map<String, Object> params)
			throws BusinessHandleException;

	public int countByParams(Map<String, Object> params)
			throws BusinessHandleException;
}

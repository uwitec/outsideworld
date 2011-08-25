package com.pss.dao;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

	public void add(T entity);

	public void delete(String id);

	public void update(T t);

	public T findById(String id);

	public T findByEntity(T t);

	public List<T> queryByEntity(T t);

	public List<T> queryByParams(Map<String, Object> params);

	public int countByEntity(T t);

	public int countByParams(Map<String, Object> params);
}

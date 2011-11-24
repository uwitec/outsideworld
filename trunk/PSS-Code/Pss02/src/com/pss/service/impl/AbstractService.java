package com.pss.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.Entity;
import com.pss.domain.repository.BaseRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.exception.EntityNotExistedException;
import com.pss.service.IBusinessService;

public abstract class AbstractService<T extends Entity> extends IdService
		implements IBusinessService<T> {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

	public abstract BaseRepository<T> repository();

	@Transactional
	@Override
	public void add(T entity) throws BusinessHandleException,
			EntityAlreadyExistedException {
		Class<?> c = entity.getClass();
		String name = StringUtils.lowerCase(c.getSimpleName());
		entity.setId(entity.getCode() + sdf.format(new Date())
				+ nextStr(name, 16 - entity.getCode().length()));
		repository().add(entity);
	}

	@Override
	public int count(Map<String, Object> params) throws BusinessHandleException {
		return repository().count(params);
	}

	@Override
	public int count(T entity) throws BusinessHandleException {
		return repository().count(entity);
	}

	@Transactional
	@Override
	public void delete(List<String> ids) throws BusinessHandleException {
		for (String id : ids) {
			try {
				repository().delete(id);
			} catch (EntityNotExistedException e) {
				throw new BusinessHandleException();
			}
		}
	}

	@Transactional
	@Override
	public void delete(String id) throws BusinessHandleException,
			EntityNotExistedException {
		repository().delete(id);
	}

	@Override
	public T find(String id) throws BusinessHandleException {
		return repository().find(id);
	}

	@Override
	public T find(T entity) throws BusinessHandleException {
		return repository().find(entity);
	}

	@Override
	public List<T> query(Map<String, Object> params)
			throws BusinessHandleException {
		return repository().query(params);
	}

	@Override
	public List<T> query(T entity) throws BusinessHandleException {
		return repository().query(entity);
	}

	@Transactional
	@Override
	public void update(T entity) throws BusinessHandleException,
			EntityNotExistedException {
		repository().update(entity);
	}

}

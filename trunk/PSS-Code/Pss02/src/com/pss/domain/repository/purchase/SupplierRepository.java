/**
 * 
 */
package com.pss.domain.repository.purchase;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.dao.purchase.SupplierMapper;
import com.pss.domain.model.entity.purchase.Supplier;
import com.pss.domain.repository.BaseRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.exception.EntityNotExistedException;

/**
 * @author liang
 *
 */
public class SupplierRepository implements BaseRepository<Supplier> {
	
	@Autowired
	private SupplierMapper supplierMapper;

	@Override
	public void add(Supplier supplier) throws BusinessHandleException,
			EntityAlreadyExistedException {
		supplierMapper.add(supplier);
	}

	@Override
	public void delete(String id) throws BusinessHandleException,
			EntityNotExistedException {
		supplierMapper.delete(id);
	}

	@Override
	public void update(Supplier supplier) throws BusinessHandleException,
			EntityNotExistedException {
		supplierMapper.update(supplier);
	}

	@Override
	public Supplier find(String id) throws BusinessHandleException {
		return supplierMapper.findById(id);
	}

	@Override
	public Supplier find(Supplier supplier) throws BusinessHandleException {
		return supplierMapper.findByEntity(supplier);
	}

	@Override
	public List<Supplier> query(Supplier supplier) throws BusinessHandleException {
		return supplierMapper.queryByEntity(supplier);
	}

	@Override
	public List<Supplier> query(Map<String, Object> params)
			throws BusinessHandleException {
		return supplierMapper.queryByParams(params);
	}

	@Override
	public int count(Supplier supplier) throws BusinessHandleException {
		return supplierMapper.countByEntity(supplier);
	}

	@Override
	public int count(Map<String, Object> params) throws BusinessHandleException {
		return supplierMapper.countByParams(params);
	}

	
}

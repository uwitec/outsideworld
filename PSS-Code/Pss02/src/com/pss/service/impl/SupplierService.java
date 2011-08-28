/**
 * 
 */
package com.pss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.purchase.Supplier;
import com.pss.domain.repository.purchase.SupplierRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.exception.EntityNotExistedException;
import com.pss.service.ISupplierService;

/**
 * @author liang
 *
 */
public class SupplierService extends AbstractService implements ISupplierService {

	@Autowired
	private SupplierRepository supplierRepository;
	
	@Transactional
	@Override
	public void add(Supplier supplier) throws BusinessHandleException,
			EntityAlreadyExistedException {
		supplier.setId(nextStr("supplier", 64));
		supplierRepository.add(supplier);
	}

	@Transactional
	@Override
	public void delete(String id) throws BusinessHandleException,
			EntityNotExistedException {
		supplierRepository.delete(id);
	}

	@Transactional
	@Override
	public void delete(List<String> ids) throws BusinessHandleException {
		for (String id : ids) {
			try {
				supplierRepository.delete(id);
			} catch (EntityNotExistedException e) {
				throw new BusinessHandleException();
			}
		}

	}

	@Transactional
	@Override
	public void update(Supplier supplier) throws BusinessHandleException,
			EntityNotExistedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Supplier find(String id) throws BusinessHandleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier find(Supplier supplier) throws BusinessHandleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> query(Supplier supplier) throws BusinessHandleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> query(Map<String, Object> params)
			throws BusinessHandleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(Supplier supplier) throws BusinessHandleException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int count(Map<String, Object> params) throws BusinessHandleException {
		// TODO Auto-generated method stub
		return 0;
	}

}

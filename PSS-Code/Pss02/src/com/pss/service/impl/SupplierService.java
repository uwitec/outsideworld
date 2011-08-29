/**
 * 
 */
package com.pss.service.impl;


import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.purchase.Supplier;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.purchase.SupplierRepository;
import com.pss.service.ISupplierService;

/**
 * @author liang
 *
 */
public class SupplierService extends AbstractService<Supplier> implements ISupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Override
	public BaseRepository<Supplier> repository() {
		// TODO Auto-generated method stub
		return supplierRepository;
	}
}

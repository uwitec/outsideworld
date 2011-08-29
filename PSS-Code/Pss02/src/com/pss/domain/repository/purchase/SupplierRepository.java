/**
 * 
 */
package com.pss.domain.repository.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.dao.BaseMapper;
import com.pss.dao.purchase.SupplierMapper;
import com.pss.domain.model.entity.purchase.Supplier;
import com.pss.domain.repository.BaseRepository;

/**
 * @author liang
 *
 */
public class SupplierRepository extends BaseRepository<Supplier> {
	
	@Autowired
	private SupplierMapper supplierMapper;

	@Override
	protected BaseMapper<Supplier> getMapper() {
		return supplierMapper;
	}

}

/**
 * 
 */
package com.pss.web.action.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.purchase.Supplier;
import com.pss.service.IBusinessService;
import com.pss.service.ISupplierService;
import com.pss.web.action.PaginationAction;

/**
 * @author liang
 *
 */
public class SupplierAction extends PaginationAction<Supplier> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ISupplierService supplierService;

	@Override
	public IBusinessService<Supplier> service() {
		return supplierService;
	}
}

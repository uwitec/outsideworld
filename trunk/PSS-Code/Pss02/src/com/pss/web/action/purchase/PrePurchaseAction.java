/**
 * 
 */
package com.pss.web.action.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.purchase.PrePurchase;
import com.pss.service.IBusinessService;
import com.pss.service.IPrePurchaseService;
import com.pss.web.action.PaginationAction;

/**
 * @author liang
 *
 */
public class PrePurchaseAction extends PaginationAction<PrePurchase> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IPrePurchaseService prePurchaseService;

	/* (non-Javadoc)
	 * @see com.pss.web.action.EntityAction#service()
	 */
	@Override
	public IBusinessService<PrePurchase> service() {
		return prePurchaseService;
	}
	
}

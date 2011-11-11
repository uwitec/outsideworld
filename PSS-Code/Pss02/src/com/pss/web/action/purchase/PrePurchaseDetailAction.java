/**
 * 
 */
package com.pss.web.action.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.purchase.PrePurchaseDetail;
import com.pss.service.IBusinessService;
import com.pss.service.IPrePurchaseDetailService;
import com.pss.web.action.DetailsPaginationAction;

/**
 * @author liang
 *
 */
public class PrePurchaseDetailAction extends DetailsPaginationAction<PrePurchaseDetail> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IPrePurchaseDetailService prePurchaseDetailService;

	/* (non-Javadoc)
	 * @see com.pss.web.action.EntityAction#service()
	 */
	@Override
	public IBusinessService<PrePurchaseDetail> service() {
		return prePurchaseDetailService;
	}

}

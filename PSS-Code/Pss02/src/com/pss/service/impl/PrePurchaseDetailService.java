/**
 * 
 */
package com.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.domain.model.entity.purchase.PrePurchaseDetail;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.purchase.PrePurchaseDetailRepository;
import com.pss.service.IPrePurchaseDetailService;

/**
 * @author liang
 *
 */
@Service
public class PrePurchaseDetailService extends AbstractService<PrePurchaseDetail> implements IPrePurchaseDetailService {

	@Autowired
	private PrePurchaseDetailRepository prePurchaseDetailRepository;
	/* (non-Javadoc)
	 * @see com.pss.service.impl.AbstractService#repository()
	 */
	@Override
	public BaseRepository<PrePurchaseDetail> repository() {
		return prePurchaseDetailRepository;
	}

}

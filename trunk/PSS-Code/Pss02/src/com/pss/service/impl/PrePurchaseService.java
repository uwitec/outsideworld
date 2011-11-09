/**
 * 
 */
package com.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.domain.model.entity.purchase.PrePurchase;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.purchase.PrePurchaseRepository;
import com.pss.service.IPrePurchaseService;

/**
 * @author liang
 *
 */
@Service
public class PrePurchaseService extends AbstractService<PrePurchase> implements IPrePurchaseService {

	@Autowired
	private PrePurchaseRepository prePurchaseRepository;
	
	/* (non-Javadoc)
	 * @see com.pss.service.impl.AbstractService#repository()
	 */
	@Override
	public BaseRepository<PrePurchase> repository() {
		return prePurchaseRepository;
	}

}

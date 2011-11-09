/**
 * 
 */
package com.pss.domain.repository.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.dao.BaseMapper;
import com.pss.dao.purchase.PrePurchaseMapper;
import com.pss.domain.model.entity.purchase.PrePurchase;
import com.pss.domain.repository.BaseRepository;

/**
 * @author liang
 *
 */
public class PrePurchaseRepository extends BaseRepository<PrePurchase> {
	
	@Autowired
	private PrePurchaseMapper prePurchaseMapper;

	@Override
	protected BaseMapper<PrePurchase> getMapper() {
		return prePurchaseMapper;
	}

}

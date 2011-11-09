/**
 * 
 */
package com.pss.domain.repository.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.dao.BaseMapper;
import com.pss.dao.purchase.PrePurchaseDetailMapper;
import com.pss.domain.model.entity.purchase.PrePurchaseDetail;
import com.pss.domain.repository.BaseRepository;

/**
 * @author liang
 *
 */
public class PrePurchaseDetailRepository extends BaseRepository<PrePurchaseDetail> {

	@Autowired
	private PrePurchaseDetailMapper prePurchaseDetailMapper;
	
	@Override
	protected BaseMapper<PrePurchaseDetail> getMapper() {
		return prePurchaseDetailMapper;
	}

}

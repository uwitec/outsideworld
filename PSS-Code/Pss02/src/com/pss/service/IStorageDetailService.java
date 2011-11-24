package com.pss.service;

import com.pss.domain.model.entity.inventory.StorageDetail;
import com.pss.exception.BusinessHandleException;

/**
 * 
 * @author Aries Zhao
 * 
 */
public interface IStorageDetailService extends IBusinessService<StorageDetail> {
	public void deleteByMasterId(String id) throws BusinessHandleException;
}

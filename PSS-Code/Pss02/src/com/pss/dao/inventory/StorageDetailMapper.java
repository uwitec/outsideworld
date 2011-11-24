package com.pss.dao.inventory;

import com.pss.dao.BaseMapper;
import com.pss.domain.model.entity.inventory.StorageDetail;
import com.pss.exception.BusinessHandleException;

public interface StorageDetailMapper extends BaseMapper<StorageDetail> {
	public void deleteByMasterId(String id) throws BusinessHandleException;
}

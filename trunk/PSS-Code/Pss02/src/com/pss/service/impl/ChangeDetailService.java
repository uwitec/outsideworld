package com.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.domain.model.entity.inventory.StorageDetail;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.inventory.ChangeDetailRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IStorageDetailService;

/**
 * 
 * @author Aries Zhao
 * 
 */
@Service
public class ChangeDetailService extends AbstractService<StorageDetail>
		implements IStorageDetailService {

	@Autowired
	private ChangeDetailRepository changeDetailRepository;

	@Override
	public BaseRepository<StorageDetail> repository() {
		return changeDetailRepository;
	}

	@Override
	public void deleteByMasterId(String id) throws BusinessHandleException {
		// TODO Auto-generated method stub
	}
}

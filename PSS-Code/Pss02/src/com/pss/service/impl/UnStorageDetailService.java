package com.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.domain.model.entity.inventory.StorageDetail;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.inventory.UnStorageDetailRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.service.IStorageDetailService;

/**
 * 
 * @author Aries Zhao
 * 
 */
@Service
public class UnStorageDetailService extends AbstractService<StorageDetail>
		implements IStorageDetailService {

	@Autowired
	private UnStorageDetailRepository unStorageDetailRepository;

	@Override
	public BaseRepository<StorageDetail> repository() {
		return unStorageDetailRepository;
	}

	@Override
	public void deleteByMasterId(String id) throws BusinessHandleException {
		// TODO Auto-generated method stub
	}
}

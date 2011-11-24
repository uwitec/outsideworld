package com.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.inventory.StorageDetail;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.inventory.StorageDetailRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IStorageDetailService;

/**
 * 
 * @author Aries Zhao
 * 
 */
@Service("StorageDetailService")
public class StorageDetailService extends AbstractService<StorageDetail>
		implements IStorageDetailService {

	@Autowired
	private StorageDetailRepository storageDetailRepository;

	@Override
	public BaseRepository<StorageDetail> repository() {
		return storageDetailRepository;
	}

	@Transactional
	@Override
	public void deleteByMasterId(String id) throws BusinessHandleException {
		storageDetailRepository.deleteByMasterId(id);
	}
}

package com.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.domain.model.entity.inventory.StorageDetail;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.inventory.StorageDetailRepository;
import com.pss.service.IStorageDetailService;

/**
 * 
 * @author Aries Zhao
 * 
 */
@Service
public class StorageDetailService extends AbstractService<StorageDetail>
		implements IStorageDetailService {

	@Autowired
	private StorageDetailRepository storageDetailRepository;

	@Override
	public BaseRepository<StorageDetail> repository() {
		return storageDetailRepository;
	}
}

package com.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.domain.model.entity.inventory.Storage;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.inventory.StorageRepository;
import com.pss.service.IStorageService;

/**
 * 
 * @author Aries Zhao
 * 
 */
@Service
public class StorageService extends AbstractService<Storage> implements
		IStorageService {

	@Autowired
	private StorageRepository storageRepository;

	@Override
	public BaseRepository<Storage> repository() {
		return storageRepository;
	}
}

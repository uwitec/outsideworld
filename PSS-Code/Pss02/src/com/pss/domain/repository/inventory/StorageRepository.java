package com.pss.domain.repository.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pss.dao.BaseMapper;
import com.pss.dao.inventory.StorageMapper;
import com.pss.domain.model.entity.inventory.Storage;
import com.pss.domain.repository.BaseRepository;

@Repository
public class StorageRepository extends BaseRepository<Storage> {

	@Autowired
	private StorageMapper storageMapper;

	@Override
	protected BaseMapper<Storage> getMapper() {
		return storageMapper;
	}
}

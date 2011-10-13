package com.pss.domain.repository.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pss.dao.BaseMapper;
import com.pss.dao.inventory.UnStorageDetailMapper;
import com.pss.domain.model.entity.inventory.StorageDetail;
import com.pss.domain.repository.BaseRepository;

@Repository
public class UnStorageDetailRepository extends BaseRepository<StorageDetail> {

	@Autowired
	private UnStorageDetailMapper unStorageDetailMapper;

	@Override
	protected BaseMapper<StorageDetail> getMapper() {
		return unStorageDetailMapper;
	}
}

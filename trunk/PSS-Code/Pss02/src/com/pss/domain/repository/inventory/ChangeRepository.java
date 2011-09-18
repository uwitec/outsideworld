package com.pss.domain.repository.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pss.dao.BaseMapper;
import com.pss.dao.inventory.ChangeMapper;
import com.pss.domain.model.entity.inventory.Storage;
import com.pss.domain.repository.BaseRepository;

@Repository
public class ChangeRepository extends BaseRepository<Storage> {

	@Autowired
	private ChangeMapper changeMapper;

	@Override
	protected BaseMapper<Storage> getMapper() {
		return changeMapper;
	}
}

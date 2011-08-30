package com.pss.domain.repository.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pss.dao.BaseMapper;
import com.pss.dao.purchase.GoodCategoryMapper;
import com.pss.domain.model.entity.purchase.GoodCategory;
import com.pss.domain.repository.BaseRepository;

/**
 * 
 * @author Aries Zhao
 * 
 */
@Repository
public class GoodCategoryRepository extends BaseRepository<GoodCategory> {

	@Autowired
	private GoodCategoryMapper goodCategoryMapper;

	@Override
	protected BaseMapper<GoodCategory> getMapper() {
		return goodCategoryMapper;
	}
}

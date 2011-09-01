package com.pss.domain.repository.purchase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pss.dao.BaseMapper;
import com.pss.dao.purchase.GoodCategoryMapper;
import com.pss.domain.model.entity.purchase.GoodCategory;
import com.pss.domain.repository.BaseRepository;
import com.pss.exception.BusinessHandleException;

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

	/* 是否是系统分类 */
	public boolean isSystemCategory(String categoryName)
			throws BusinessHandleException {
		if (goodCategoryMapper.findSystemCategory(categoryName) == null)
			return false;
		else
			return true;
	}

	/* 自定义分类是否重复 */
	public boolean isTenantCategory(GoodCategory goodCategory)
			throws BusinessHandleException {
		if (goodCategoryMapper.findTenantCategory(goodCategory) == null)
			return false;
		else
			return true;
	}

	/* 前缀查询 */
	public List<GoodCategory> queryByPrefix(GoodCategory goodCategory)
			throws BusinessHandleException {
		return goodCategoryMapper.queryByPrefix(goodCategory);
	}
}

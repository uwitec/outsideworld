package com.pss.dao.purchase;

import java.util.List;

import com.pss.dao.BaseMapper;
import com.pss.domain.model.entity.purchase.GoodCategory;
import com.pss.exception.BusinessHandleException;

/**
 * 
 * @author Aries Zhao
 * 
 */
public interface GoodCategoryMapper extends BaseMapper<GoodCategory> {

	public GoodCategory findSystemCategory(String categoryName)
			throws BusinessHandleException;

	public GoodCategory findTenantCategory(GoodCategory goodCategory)
			throws BusinessHandleException;

	public List<GoodCategory> queryByPrefix(String categoryName)
			throws BusinessHandleException;
}

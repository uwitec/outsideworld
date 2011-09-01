package com.pss.service;

import java.util.List;

import com.pss.domain.model.entity.purchase.GoodCategory;
import com.pss.exception.BusinessHandleException;

/**
 * 
 * @author Aries Zhao
 * 
 */
public interface IGoodCategoryService extends IBusinessService<GoodCategory> {

	public boolean isSystemCategory(String categoryName)
			throws BusinessHandleException;

	public boolean isTenantCategory(GoodCategory goodCategory)
			throws BusinessHandleException;
	
	public List<GoodCategory> queryByPrefix(GoodCategory goodCategory)
	throws BusinessHandleException;
}

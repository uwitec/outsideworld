package com.pss.service;

import com.pss.domain.model.entity.purchase.GoodCategory;

/**
 * 
 * @author Aries Zhao
 * 
 */
public interface IGoodCategoryService extends IBusinessService<GoodCategory> {

	public boolean isSystemCategory(String categoryName);

	public boolean isTenantCategory(GoodCategory goodCategory);
}

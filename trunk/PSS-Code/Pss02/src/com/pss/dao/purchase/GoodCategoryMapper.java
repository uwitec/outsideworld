package com.pss.dao.purchase;

import com.pss.dao.BaseMapper;
import com.pss.domain.model.entity.purchase.GoodCategory;

/**
 * 
 * @author Aries Zhao
 * 
 */
public interface GoodCategoryMapper extends BaseMapper<GoodCategory> {

	public GoodCategory findSystemCategory(String categoryName);

	public GoodCategory findTenantCategory(GoodCategory goodCategory);
}

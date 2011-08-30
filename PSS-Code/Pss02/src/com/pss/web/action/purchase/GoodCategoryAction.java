package com.pss.web.action.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.purchase.GoodCategory;
import com.pss.service.IBusinessService;
import com.pss.service.IGoodCategoryService;
import com.pss.web.action.PaginationAction;

public class GoodCategoryAction extends
		PaginationAction<com.pss.domain.model.entity.purchase.GoodCategory> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IGoodCategoryService goodCategoryService;

	@Override
	public IBusinessService<GoodCategory> service() {
		return goodCategoryService;
	}

}

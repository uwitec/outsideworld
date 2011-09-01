package com.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.domain.model.entity.purchase.GoodCategory;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.purchase.GoodCategoryRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IGoodCategoryService;

@Service
public class GoodCategoryService extends AbstractService<GoodCategory>
		implements IGoodCategoryService {

	@Autowired
	private GoodCategoryRepository goodCategoryRepository;

	@Override
	public BaseRepository<GoodCategory> repository() {
		return goodCategoryRepository;
	}

	@Override
	public boolean isSystemCategory(String categoryName)
			throws BusinessHandleException {
		return goodCategoryRepository.isSystemCategory(categoryName);
	}

	@Override
	public boolean isTenantCategory(GoodCategory goodCategory)
			throws BusinessHandleException {
		return goodCategoryRepository.isTenantCategory(goodCategory);
	}
}

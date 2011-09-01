package com.pss.domain.model.entity.purchase;

import com.pss.common.annotation.FieldValidation;
import com.pss.domain.model.entity.Entity;
import com.pss.domain.repository.purchase.GoodCategoryRepository;
import com.pss.exception.BusinessHandleException;

/**
 * 
 * @author Aries Zhao
 * 
 */
public class GoodCategory extends Entity {

	@FieldValidation(isBlank = false)
	private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String findIdByName(GoodCategoryRepository goodCategoryRepository) throws BusinessHandleException {
		return goodCategoryRepository.find(this).getId();
	}
}

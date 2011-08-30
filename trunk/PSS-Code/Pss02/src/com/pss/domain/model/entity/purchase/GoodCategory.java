package com.pss.domain.model.entity.purchase;

import com.pss.common.annotation.FieldValidation;
import com.pss.domain.model.entity.Entity;

/**
 * 
 * @author Aries Zhao
 * 
 */
public class GoodCategory extends Entity {

	@FieldValidation(isBlank = false)
	private String categoryName;

	private String categoryDesc;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		this.categoryDesc = categoryName;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
}

package com.pss.web.action.purchase;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.purchase.GoodCategory;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IBusinessService;
import com.pss.service.IGoodCategoryService;
import com.pss.web.action.PaginationAction;

public class GoodCategoryAction extends
		PaginationAction<com.pss.domain.model.entity.purchase.GoodCategory> {

	private static final long serialVersionUID = 1L;

	private boolean hideSysCate = false;
	
	private String value;

	@Autowired
	private IGoodCategoryService goodCategoryService;

	@Override
	public IBusinessService<GoodCategory> service() {
		return goodCategoryService;
	}

	@Override
	public String queryEntity() {
		String tanentId = getTenantId();
		if (StringUtils.isBlank(tanentId)) {
			return INPUT;
		}
		try {
			entity.setTenant(getTenantId());
			getQuery().put("entity", entity);
			getQuery().put("hideSysCate", hideSysCate);
			totalCount = service().count(getQuery());
			entity.setTenant(getTenantId());
			items = service().query(getQuery());
		} catch (BusinessHandleException e) {
			e.printStackTrace();
			setCorrect(false);
			return ERROR;
		}
		setCorrect(true);
		return SUCCESS;
	}

	public String validateAddEntity() {
		if (ERROR.equals(validateCategoryName())) {
			addActionError("货品分类名称已经存在");
			setCorrect(false);
			return SUCCESS;
		} else {
			setCorrect(true);
			return SUCCESS;
		}
	}

	public String validateCategoryName() {
		try {
			if (goodCategoryService.isSystemCategory(entity.getCategoryName())) {
				setFieldError("货品分类名称已经在系统分类中存在");
				return ERROR;
			}

			entity.setTenant(getTenantId());
			if (goodCategoryService.isTenantCategory(entity)) {
				setFieldError("货品分类名称已经在自定义分类中存在");
				return ERROR;
			}
		} catch (BusinessHandleException e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public void setHideSysCate(String hideSysCate) {
		this.hideSysCate = new Boolean(hideSysCate);
	}
	
	public String category(){
		GoodCategory goodCategory = new GoodCategory();
		try {
			goodCategory.setTenant(getTenantId());
			goodCategory.setCategoryName(value);
			items = goodCategoryService.queryByPrefix(goodCategory);
		} catch (BusinessHandleException e) {
			e.printStackTrace();
			setCorrect(false);
			return ERROR;
		}
		setCorrect(true);
		return SUCCESS;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}

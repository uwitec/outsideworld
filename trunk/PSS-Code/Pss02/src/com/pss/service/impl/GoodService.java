/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.service.impl.GoodService.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Aug 29, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.purchase.Good;
import com.pss.domain.model.entity.purchase.GoodCategory;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.purchase.GoodCategoryRepository;
import com.pss.domain.repository.purchase.GoodRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.exception.EntityNotExistedException;
import com.pss.service.IGoodService;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Aug 29, 2011
 */
public class GoodService extends AbstractService<Good> implements IGoodService{

	@Autowired
	private GoodRepository goodRepository;
	@Autowired
	private GoodCategoryRepository goodCategoryRepository;

	@Override
	public BaseRepository<Good> repository() {
		return goodRepository;
	}
    @Transactional
	@Override
	public void add(Good entity) throws BusinessHandleException,
			EntityAlreadyExistedException {
    	resetCategory(entity);
		if(entity.findByLogic(goodRepository)){
			throw new BusinessHandleException("category.logic.repeate");
		}	
		super.add(entity);
	}
    
    @Transactional
	@Override
	public void update(Good entity) throws BusinessHandleException,
			EntityNotExistedException {
    	resetCategory(entity);
    	if(entity.findByLogic(goodRepository)){
			throw new BusinessHandleException("category.logic.repeate");
		}
    	if(goodRepository.find(entity.getId())==null){
    		throw new BusinessHandleException("date.deleted");
    	}
		super.update(entity);
	}
    
    /**
     * 重新将分类名设置成分类id
     * @param entity
     * @throws BusinessHandleException
     */
    private void resetCategory(Good entity)throws BusinessHandleException{
    	//将分类名称转化为分类id
		String name = entity.getCategory();
		if(StringUtils.isBlank(name)){
			throw new BusinessHandleException("category.name.isNull");
		}
		GoodCategory goodCategory = new GoodCategory();
		goodCategory.setCategoryName(name);
		goodCategory.setTenant(entity.getTenant());
		entity.setCategory(goodCategory.findIdByName(goodCategoryRepository));
    }
}

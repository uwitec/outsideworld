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

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.purchase.Good;
import com.pss.domain.repository.BaseRepository;
import com.pss.domain.repository.purchase.GoodRepository;
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

	@Override
	public BaseRepository<Good> repository() {
		return goodRepository;
	}
	
	

}

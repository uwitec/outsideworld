/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.domain.repository.purchase.GoodRepository.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Aug 29, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.domain.repository.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.dao.BaseMapper;
import com.pss.dao.purchase.GoodMapper;
import com.pss.domain.model.entity.purchase.Good;
import com.pss.domain.repository.BaseRepository;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Aug 29, 2011
 */
public class GoodRepository extends BaseRepository<Good> {

	@Autowired
	private GoodMapper goodMapper;
	@Override
	protected BaseMapper<Good> getMapper() {
		return goodMapper;
	}

}

/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.web.action.purchase.GoodAction.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Aug 29, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.web.action.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.purchase.Good;
import com.pss.service.IBusinessService;
import com.pss.service.IGoodService;
import com.pss.web.action.PaginationAction;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Aug 29, 2011
 */
public class GoodAction extends PaginationAction<Good> {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IGoodService goodService;
	@Override
	public IBusinessService<Good> service() {
		return goodService;
	}

}

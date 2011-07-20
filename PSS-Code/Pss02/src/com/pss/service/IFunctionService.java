/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.service.IFunctionService.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jul 20, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.service;

import java.util.List;

import com.pss.domain.model.entity.sys.Function;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Jul 20, 2011
 */
public interface IFunctionService {
	/**
	 * 根据传入的角色Id,获得相应的Function
	 * @param roleId
	 * @return
	 */
    public List<Function> obtainFunction(String roleId);
}

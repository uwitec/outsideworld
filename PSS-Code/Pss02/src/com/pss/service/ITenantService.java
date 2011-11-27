/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.service.ITenantService.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jun 27, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.service;

import com.pss.domain.model.entity.sys.Tenant;
import com.pss.exception.BusinessHandleException;

/**
 * <p>
 * 类说明
 * </p>
 * <p>
 * Copyright: 版权所有 (c) 2010 - 2030
 * </p>
 * <p>
 * Company: Travelsky
 * </p>
 * 
 * @author Travelsky
 * @version 1.0
 * @since Jun 27, 2011
 */
public interface ITenantService {
	public String regist(Tenant tenant) throws BusinessHandleException;

	public String vAcount(String account) throws BusinessHandleException;

	public String vEmail(String email) throws BusinessHandleException;

	public Tenant findById(String id) throws BusinessHandleException;
}

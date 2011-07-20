/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.service.impl.FunctionService.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jul 20, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.service.impl;

import java.util.List;

import com.pss.domain.model.entity.sys.Function;
import com.pss.domain.repository.system.RoleRepository;
import com.pss.service.IFunctionService;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Jul 20, 2011
 */
public class FunctionService implements IFunctionService{
    private RoleRepository roleRepository;
	@Override
	public List<Function> obtainFunction(String roleId) {
		return null;
	}
	public RoleRepository getRoleRepository() {
		return roleRepository;
	}
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
    
	
}

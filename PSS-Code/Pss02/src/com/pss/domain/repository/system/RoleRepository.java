/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.domain.repository.system.FunctionRepository.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jul 20, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.domain.repository.system;

import java.util.List;

import com.pss.dao.system.RoleMapper;
import com.pss.domain.model.entity.sys.Function;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Jul 20, 2011
 */
public class RoleRepository {
	private RoleMapper roleMapper;
	/**
	 * 根据所传入的roleId获得相应的List
	 * @param roleId
	 * @return
	 */
	public List<Function> obtainFunction(String roleId) {		
		return roleMapper.obtainFunction(roleId);
	}
	public RoleMapper getRoleMapper() {
		return roleMapper;
	}
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}
	
	
}

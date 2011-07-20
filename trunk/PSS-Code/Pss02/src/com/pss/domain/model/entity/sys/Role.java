/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.domain.model.entity.sys.Role.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jun 21, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.domain.model.entity.sys;

import java.util.List;

import com.pss.domain.model.entity.IEntity;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Jun 21, 2011
 */
public class Role implements IEntity {
    private String roleId;
    private String roleName;
    private String roleDesc;
    private List<RolePrivalige> rolePrivaliges;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public List<RolePrivalige> getRolePrivaliges() {
		return rolePrivaliges;
	}
	public void setRolePrivaliges(List<RolePrivalige> rolePrivaliges) {
		this.rolePrivaliges = rolePrivaliges;
	}
	
    
    
}

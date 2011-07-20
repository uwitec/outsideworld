package com.pss.dao.system;

import java.util.List;

import com.pss.domain.model.entity.sys.Function;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Jun 27, 2011
 */
public interface RoleMapper {
	List<Function> obtainFunction(String roleId);
}

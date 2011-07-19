package com.pss.dao.system;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.sys.Tenant;

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
@Repository
@Transactional
public interface TenantMapper {
	void addTenant(Tenant tenant);

	Integer queryByName(String tenantName);

	String findIdByName(String tenantName);

	Integer queryByEmail(String email);
}

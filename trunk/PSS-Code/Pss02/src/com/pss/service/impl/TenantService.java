package com.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.domain.model.entity.sys.Tenant;
import com.pss.domain.repository.system.TenantRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.service.ITenantService;

@Service
public class TenantService implements ITenantService {

	@Autowired
	private TenantRepository tenantRepository;

	@Override
	public String regist(Tenant tenant) throws BusinessHandleException {
		tenantRepository.add(tenant);
		return null;
	}
}

package com.pss.service.impl;

import org.apache.commons.lang.StringUtils;
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

	@Override
	public String vAcount(String account) throws BusinessHandleException {
		if (StringUtils.isBlank(account)) {
			return "accountNotBlank";
		} else {
			Tenant tenant = new Tenant();
			tenant.setTenantName(account);
			if (tenant.isNameExist(tenantRepository)) {
				return "accountExist";
			}
		}
		return "";
	}

	@Override
	public String vEmail(String email) throws BusinessHandleException {
		if (StringUtils.isBlank(email)) {
			return "emailNotBlank";
		} else {
			Tenant tenant = new Tenant();
			tenant.setTenantEmail(email);
			if (tenant.isNameExist(tenantRepository)) {
				return "emailExist";
			}
		}
		return "";
	}
	
}

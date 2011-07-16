package com.pss.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pss.domain.model.entity.sys.Tenant;
import com.pss.domain.repository.system.TenantRepository;
import com.pss.domain.repository.system.UserRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.service.ITenantService;

@Service
public class TenantService implements ITenantService {

	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public String regist(Tenant tenant) throws BusinessHandleException {
		String vAcount = vAcount(tenant.getTenantName());
		if(!StringUtils.isBlank(vAcount)){
			return vAcount;
		}
		String vEmail = vEmail(tenant.getTenantEmail());
		if(!StringUtils.isBlank(vEmail)){
			return vEmail;
		}
		tenantRepository.add(tenant);
		return "";
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

	public TenantRepository getTenantRepository() {
		return tenantRepository;
	}

	public void setTenantRepository(TenantRepository tenantRepository) {
		this.tenantRepository = tenantRepository;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
}

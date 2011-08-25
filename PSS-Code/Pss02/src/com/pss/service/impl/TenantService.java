package com.pss.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.sys.Role;
import com.pss.domain.model.entity.sys.Tenant;
import com.pss.domain.model.entity.sys.User;
import com.pss.domain.repository.system.TenantRepository;
import com.pss.domain.repository.system.UserRepository;
import com.pss.exception.BusinessHandleException;
import com.pss.exception.EntityAlreadyExistedException;
import com.pss.service.ITenantService;

public class TenantService extends AbstractService implements ITenantService {
	private TenantRepository tenantRepository;
	private UserRepository userRepository;

	@Transactional
	@Override
	public String regist(Tenant tenant) throws BusinessHandleException {
		String vAcount = vAcount(tenant.getTenantName());
		if (!StringUtils.isBlank(vAcount)) {
			return vAcount;
		}
		String vEmail = vEmail(tenant.getTenantEmail());
		if (!StringUtils.isBlank(vEmail)) {
			return vEmail;
		}
		tenant.setTenantId(nextStr("tenant", 64));
		tenantRepository.add(tenant);
		User user = new User();
		user.setUserId(nextStr("user", 64));
		user.setUserName(tenant.getTenantName());
		user.setUserPassword(tenant.getTenantPassword());
		Role role = new Role();
		role.setRoleId("0");
		user.setRole(role);
		user.setTenant(tenant.getTenantId());
		try {
			userRepository.add(user);
		} catch (EntityAlreadyExistedException e) {
			e.printStackTrace();
		}
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

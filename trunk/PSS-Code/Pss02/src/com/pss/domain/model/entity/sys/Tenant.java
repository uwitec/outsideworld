/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.domain.model.entity.sys.Tenant.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jun 27, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.domain.model.entity.sys;

import java.util.Date;

import com.pss.domain.model.entity.IEntity;
import com.pss.domain.repository.system.TenantRepository;
import com.pss.exception.BusinessHandleException;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Jun 27, 2011
 */
public class Tenant implements IEntity {
    private String tenantId;
    private String tenantName;
    private String tenantPassword;
    private String tenantCountry;
    private String tenantCity;
    private String tenantProvince;
    private String tenantEmail;
    private Integer userNum;
    private Date creatTime;
    private String status;
    private String note;
    
    /**
     * 业务方法，根据名称查找用户名是否已经存在
     * @param tenantRepository
     * @return
     * @throws BusinessHandleException
     */
    public boolean isNameExist(TenantRepository tenantRepository) throws BusinessHandleException {    	
    	if(tenantRepository.queryByName(tenantName)>0){
    		return true;
    	}
    	return false;
    }
    
    public boolean  isEmailExsit(TenantRepository tenantRepository) throws BusinessHandleException {
    	if(tenantRepository.queryByName(tenantName)>0){
    		return true;
    	}
    	return false;
    }
    
    
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantPassword() {
		return tenantPassword;
	}
	public void setTenantPassword(String tenantPassword) {
		this.tenantPassword = tenantPassword;
	}
	public String getTenantCountry() {
		return tenantCountry;
	}
	public void setTenantCountry(String tenantCountry) {
		this.tenantCountry = tenantCountry;
	}
	public String getTenantCity() {
		return tenantCity;
	}
	public void setTenantCity(String tenantCity) {
		this.tenantCity = tenantCity;
	}
	public String getTenantProvince() {
		return tenantProvince;
	}
	public void setTenantProvince(String tenantProvince) {
		this.tenantProvince = tenantProvince;
	}
	public String getTenantEmail() {
		return tenantEmail;
	}
	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}
	public Integer getUserNum() {
		return userNum;
	}
	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
    
    
}

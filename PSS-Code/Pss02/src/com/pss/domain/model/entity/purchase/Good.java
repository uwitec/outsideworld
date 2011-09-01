/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.domain.model.entity.purchase.Good.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Aug 29, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.domain.model.entity.purchase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pss.domain.model.entity.Entity;
import com.pss.domain.repository.purchase.GoodRepository;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Aug 29, 2011
 */
public class Good extends Entity {
    private String name;
    private String category;
    private String specification;
    private String unit;
    private String purchasePrice;
    private BigDecimal salePrice;
    private Integer inventory;
    private String barcode;
    private String status;
    private String lastUpdateUser;
    private Date lastUpdateDate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	} 
	
	public boolean logicEquals(Good other){
		return StringUtils.equals(name,other.getName())&&StringUtils.equals(category,other.getCategory())&&StringUtils.equals(specification,other.getSpecification());
	}
	
	public boolean findByLogic(GoodRepository goodRepository){
		List<Good> result = goodRepository.findByLogic(this);
		if(result!=null&&result.size()>0){
			for(Good g:result){
				if(!StringUtils.isBlank(getId())&&!StringUtils.equals(getId(), g.getId())){
					return true;
				}
			}
			
		}
		return false;
	}
}

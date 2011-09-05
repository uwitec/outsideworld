/**
 * 
 */
package com.pss.domain.model.entity.purchase;

import java.util.List;

import com.pss.domain.model.entity.Entity;

/**
 * @author liang
 *
 */
public class PrePurchase extends Entity {

	private String status;
	private List<PrePurchaseDetail> prePruchaseDetails;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<PrePurchaseDetail> getPrePruchaseDetails() {
		return prePruchaseDetails;
	}
	public void setPrePruchaseDetails(List<PrePurchaseDetail> prePruchaseDetails) {
		this.prePruchaseDetails = prePruchaseDetails;
	}
}

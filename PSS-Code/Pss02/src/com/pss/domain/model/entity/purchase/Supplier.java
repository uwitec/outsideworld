/**
 * 
 */
package com.pss.domain.model.entity.purchase;

import com.pss.common.annotation.FieldValidation;
import com.pss.domain.model.entity.Entity;

/**
 * @author liang
 *
 */
public class Supplier extends Entity {

	@FieldValidation(isBlank = false)
	private String supplierName;
	
	private String supplierAddress;
	
	private String supplierZipCode;
	
	private String supplierContact;
	
	private String supplierTel;
	
	private String supplierNote;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getSupplierZipCode() {
		return supplierZipCode;
	}

	public void setSupplierZipCode(String supplierZipCode) {
		this.supplierZipCode = supplierZipCode;
	}

	public String getSupplierContact() {
		return supplierContact;
	}

	public void setSupplierContact(String supplierContact) {
		this.supplierContact = supplierContact;
	}

	public String getSupplierTel() {
		return supplierTel;
	}

	public void setSupplierTel(String supplierTel) {
		this.supplierTel = supplierTel;
	}

	public String getSupplierNote() {
		return supplierNote;
	}

	public void setSupplierNote(String supplierNote) {
		this.supplierNote = supplierNote;
	}
	
}

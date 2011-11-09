/**
 * 
 */
package com.pss.domain.model.entity.purchase;

import com.pss.domain.model.entity.Entity;

/**
 * @author liang
 * 
 * 采购单明细
 *
 */
public class PrePurchaseDetail extends Entity {
	
	private String prePurchaseID;
	private String prePurchaseSupplier;
	private String prePurchaseGoods;
	private String prePurchaseSpecification;
	private int prePurchaseAmount;
	private String prePurchaseUnit;
	private double prePurchasePrice;
	private double preRefPurchasePrice;
	private int prePurchaseInventory;
	private int prePurchaseDepreciation;
	private String prePurchaseNote;
	
	public String getPrePurchaseID() {
		return prePurchaseID;
	}
	public void setPrePurchaseID(String prePurchaseID) {
		this.prePurchaseID = prePurchaseID;
	}
	public String getPrePurchaseSupplier() {
		return prePurchaseSupplier;
	}
	public void setPrePurchaseSupplier(String prePurchaseSupplier) {
		this.prePurchaseSupplier = prePurchaseSupplier;
	}
	public String getPrePurchaseGoods() {
		return prePurchaseGoods;
	}
	public void setPrePurchaseGoods(String prePurchaseGoods) {
		this.prePurchaseGoods = prePurchaseGoods;
	}
	public String getPrePurchaseSpecification() {
		return prePurchaseSpecification;
	}
	public void setPrePurchaseSpecification(String prePurchaseSpecification) {
		this.prePurchaseSpecification = prePurchaseSpecification;
	}
	public int getPrePurchaseAmount() {
		return prePurchaseAmount;
	}
	public void setPrePurchaseAmount(int prePurchaseAmount) {
		this.prePurchaseAmount = prePurchaseAmount;
	}
	public String getPrePurchaseUnit() {
		return prePurchaseUnit;
	}
	public void setPrePurchaseUnit(String prePurchaseUnit) {
		this.prePurchaseUnit = prePurchaseUnit;
	}
	public double getPrePurchasePrice() {
		return prePurchasePrice;
	}
	public void setPrePurchasePrice(double prePurchasePrice) {
		this.prePurchasePrice = prePurchasePrice;
	}
	public double getPreRefPurchasePrice() {
		return preRefPurchasePrice;
	}
	public void setPreRefPurchasePrice(double preRefPurchasePrice) {
		this.preRefPurchasePrice = preRefPurchasePrice;
	}
	public int getPrePurchaseInventory() {
		return prePurchaseInventory;
	}
	public void setPrePurchaseInventory(int prePurchaseInventory) {
		this.prePurchaseInventory = prePurchaseInventory;
	}
	public int getPrePurchaseDepreciation() {
		return prePurchaseDepreciation;
	}
	public void setPrePurchaseDepreciation(int prePurchaseDepreciation) {
		this.prePurchaseDepreciation = prePurchaseDepreciation;
	}
	public String getPrePurchaseNote() {
		return prePurchaseNote;
	}
	public void setPrePurchaseNote(String prePurchaseNote) {
		this.prePurchaseNote = prePurchaseNote;
	}
}

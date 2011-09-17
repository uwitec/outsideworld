package com.pss.domain.model.entity.inventory;

import com.pss.domain.model.entity.Entity;

/**
 * 入库单明细
 * 
 * @author Aries Zhao
 * 
 */
public class StorageDetail extends Entity {
	private String storeId;

	private String goodName;

	private String goodId;

	private String goodSpec;

	private int goodNumber;

	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getGoodSpec() {
		return goodSpec;
	}

	public void setGoodSpec(String goodSpec) {
		this.goodSpec = goodSpec;
	}

	public int getGoodNumber() {
		return goodNumber;
	}

	public void setGoodNumber(int goodNumber) {
		this.goodNumber = goodNumber;
	}
}

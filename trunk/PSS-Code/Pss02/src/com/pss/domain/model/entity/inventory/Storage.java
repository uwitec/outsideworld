package com.pss.domain.model.entity.inventory;

import com.pss.domain.model.entity.Entity;

/**
 * 入库单
 * 
 * @author Aries Zhao
 * 
 */
public class Storage extends Entity {

	private int status;
	private String note;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}

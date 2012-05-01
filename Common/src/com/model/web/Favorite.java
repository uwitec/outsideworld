package com.model.web;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/*收藏夹*/
@Entity
@Table(name = "favorite")
public class Favorite {

	/* 用户ID */
	@Column(nullable = false)
	private String userId;

	/* 舆情ID */
	@Column(nullable = false)
	private String infoId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
}

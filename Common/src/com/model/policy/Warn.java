package com.model.policy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "warn")
public class Warn {

	/* 主题ID */
	@Column(nullable = false)
	private int topicId;

	/* 用户ID */
	@Column(nullable = false)
	private int userId;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}

package com.model.web;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "topic")
public class GroupTopic {

	/* 用户组ID */
	@Column(nullable = false)
	private int groupId;

	/* 主题ID */
	@Column(nullable = false)
	private int topicId;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
}

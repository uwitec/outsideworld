package com.model.policy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "topic_source")
public class TopicSource {

	/* 主题ID */
	@Column(nullable = false)
	private int topicId;

	/* 数据源ID */
	@Column(nullable = false)
	private int sourceId;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
}

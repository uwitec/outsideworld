package com.model.policy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "source")
public class Source {

	/* ID */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;

	/* 分类ID */
	@Column(nullable = false)
	private int categoryId;

	/* 名称 */
	@Column(nullable = false, length = 200, unique = true)
	private String name;

	/* URL */
	@Column(nullable = false, length = 200, unique = true)
	private String url;

	/* 类型 */
	@Enumerated(EnumType.STRING)
	private SourceType type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SourceType getType() {
		return type;
	}

	public void setType(SourceType type) {
		this.type = type;
	}

	public static enum SourceType {
		WEBSITE, WEIBO, SEARCH, QQ
	}
}

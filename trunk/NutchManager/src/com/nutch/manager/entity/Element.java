package com.nutch.manager.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "site")
public class Element {

	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;

	@ManyToOne(targetEntity = Site.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "site_id")
	private Site siteId;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false)
	private int type;

	@Column(nullable = false, length = 200)
	private String define;

	@Column(nullable = false)
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Site getSiteId() {
		return siteId;
	}

	public void setSiteId(Site siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDefine() {
		return define;
	}

	public void setDefine(String define) {
		this.define = define;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

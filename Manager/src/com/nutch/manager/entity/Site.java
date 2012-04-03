package com.nutch.manager.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "site")
public class Site {

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 64)
	private String id;

	@OneToMany(targetEntity = Template.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "site_id")
	private Set<Template> tempaltes = new HashSet<Template>();

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 200)
	private String url;

	@Column(nullable = false)
	private int type;

	@Column(name = "fetch_interval", nullable = false)
	private int interval;

	@Column(nullable = false)
	private int depth;

	@Column
	private String metadata;

	@Column
	private Date updateDTTM;

	@Column(nullable = false)
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public Date getUpdateDTTM() {
		return updateDTTM;
	}

	public void setUpdateDTTM(Date updateDTTM) {
		this.updateDTTM = updateDTTM;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Set<Template> getTempaltes() {
		return tempaltes;
	}

	public void setTempaltes(Set<Template> tempaltes) {
		this.tempaltes = tempaltes;
	}
}

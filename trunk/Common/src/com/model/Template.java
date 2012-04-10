package com.model;

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
@Table(name = "template")
public class Template {

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 64)
	private String id;

	@OneToMany(targetEntity = Element.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "template_id")
	private Set<Element> elements = new HashSet<Element>();

	@Column(nullable = false)
	private String domain;

	@Column(name = "url_regex", nullable = false)
	private String urlRegex;
	
	@Column(name = "fetch_interval")
	private int fetchInterval;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Element> getElements() {
		return elements;
	}

	public void setElements(Set<Element> elements) {
		this.elements = elements;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUrlRegex() {
		return urlRegex;
	}

	public void setUrlRegex(String urlRegex) {
		this.urlRegex = urlRegex;
	}

	public int getFetchInterval() {
		return fetchInterval;
	}

	public void setFetchInterval(int fetchInterval) {
		this.fetchInterval = fetchInterval;
	}
}

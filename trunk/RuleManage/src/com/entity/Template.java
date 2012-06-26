package com.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "template")
public class Template implements Model{
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id=-1;
	@OneToMany(targetEntity = Element.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "template_id")
	private Set<Element> elements = new HashSet<Element>();

	@Column(nullable = false, length = 200)
	private String domain;

	@Column(name = "url_regex", nullable = false)
	private String urlRegex;

	/* 资源类型 */
	@Column(nullable = false)
	private String type;

	@ManyToOne(targetEntity = Source.class)
	private Source source;

	@Transient
	private Pattern pattern;

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
		this.pattern = Pattern.compile(urlRegex);
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean match(String url) {
		if (this.pattern == null) {
			this.pattern = Pattern.compile(urlRegex);
		}

		Matcher matcher = this.pattern.matcher(url);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

package com.model.policy;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

	@OneToMany(targetEntity = Template.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "source_id")
	private Set<Template> tempaltes = new HashSet<Template>();

	/* 名称 */
	@Column(nullable = false, length = 200)
	private String name;

	/* URL */
	@Column(name = "entry", nullable = false, length = 200, unique = true)
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

	public Set<Template> getTempaltes() {
		return tempaltes;
	}

	public void setTempaltes(Set<Template> tempaltes) {
		this.tempaltes = tempaltes;
	}

	public static enum SourceType {
		WEBSITE, WEIBO, QQ, METASE
	}
}

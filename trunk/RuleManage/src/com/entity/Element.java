package com.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "element")
public class Element implements Model{
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id=-1;
	
	

	@Column(nullable = false, length = 100)
	@NotNull
	private String name;

	@Column(nullable = false, length = 200)
	@NotNull
	private String define;

	@Column(length = 200)
	private String regex;

	@Transient
	private Pattern pattern;

	public String match(String text) {
		if (regex == null) {
			return text;
		}
		if (pattern == null) {
			pattern = Pattern.compile(regex);
		}
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return text;
		}
	}

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

	public String getDefine() {
		return define;
	}

	public void setDefine(String define) {
		this.define = define;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}

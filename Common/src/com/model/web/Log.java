package com.model.web;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "log")
public class Log {

	@Column(nullable = false)
	private String type;

	@Column(nullable = false, length = 500)
	private String message;

	@Column(nullable = false)
	private Date dttm;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDttm() {
		return dttm;
	}

	public void setDttm(Date dttm) {
		this.dttm = dttm;
	}
}

package com.model.policy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/*
 * 舆情主题，例如：金融，银行等
 */
@Entity
@Table(name = "topic")
public class Topic {

	/* ID */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;

	/* 主题名称 */
	@Column(nullable = false, unique = true, length = 200)
	private String name;

	/* 必须有的关键词 */
	@Column(nullable = false, length = 200)
	private String include;

	/* 可以有的关键词 */
	@Column(length = 200)
	private String optional;

	/* 不能有的关键词 */
	@Column(length = 200)
	private String exclude;

	/* 是否负面 */
	@Column(nullable = false, name = "isnegative")
	private boolean isNegative = false;

	/* 开启报警 */
	@Column(nullable = false, name = "iswarn")
	private boolean enableWarning = false;

	/* 报警条件 */
	@Column(nullable = false, name = "warn_limit")
	private int warningLimit;

	/* 报警方式 */
//	@Enumerated(EnumType.STRING)
//	@Column(name = "warn_type")
//	private WarnType warnType;

	/* 邮件标题or短信内容 */
//	@Column(length = 300, name = "warn_title")
//	private String warningTitle;

	/* 禁用 */
	@Column(name = "disabled", nullable = false)
	private boolean disable = false;

	@Column(name = "expire_dttm")
	private Date expireDTTM;

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

	public boolean isNegative() {
		return isNegative;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}

	public String getOptional() {
		return optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public void setNegative(boolean isNegative) {
		this.isNegative = isNegative;
	}

	public boolean isEnableWarning() {
		return enableWarning;
	}

	public void setEnableWarning(boolean enableWarning) {
		this.enableWarning = enableWarning;
	}

	public int getWarningLimit() {
		return warningLimit;
	}

	public void setWarningLimit(int warningLimit) {
		this.warningLimit = warningLimit;
	}

//	public WarnType getWarnType() {
//		return warnType;
//	}
//
//	public void setWarnType(WarnType warnType) {
//		this.warnType = warnType;
//	}

//	public String getWarningTitle() {
//		return warningTitle;
//	}
//
//	public void setWarningTitle(String warningTitle) {
//		this.warningTitle = warningTitle;
//	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public static enum WarnType {
		EMAIL, SMS
	}

	public Date getExpireDTTM() {
		return expireDTTM;
	}

	public void setExpireDTTM(Date expireDTTM) {
		this.expireDTTM = expireDTTM;
	}
}

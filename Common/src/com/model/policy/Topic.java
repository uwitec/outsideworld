package com.model.policy;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column
	private String name;

	/* 必须有的关键词 */
	@Column
	private String mustHave;

	/* 可以有的关键词 */
	@Column
	private String mayHave;

	/* 不能有的关键词 */
	@Column
	private String cannotHave;

	/* 是否负面 */
	@Column
	private boolean isNegative;

	/* 开启报警 */
	@Column
	private boolean enableWarning;

	/* 报警条件 */
	@Column
	private int warningLimit;

	/* 报警方式 */
	@Column
	private String warningType;

	/* 邮件标题or短信内容 */
	@Column
	private String warningTitle;

	/* 禁用 */
	private boolean disable;

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

	public String getMustHave() {
		return mustHave;
	}

	public void setMustHave(String mustHave) {
		this.mustHave = mustHave;
	}

	public String getMayHave() {
		return mayHave;
	}

	public void setMayHave(String mayHave) {
		this.mayHave = mayHave;
	}

	public String getCannotHave() {
		return cannotHave;
	}

	public void setCannotHave(String cannotHave) {
		this.cannotHave = cannotHave;
	}

	public boolean isNegative() {
		return isNegative;
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

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

	public String getWarningTitle() {
		return warningTitle;
	}

	public void setWarningTitle(String warningTitle) {
		this.warningTitle = warningTitle;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}
}

package com.model.policy;

/*
 * 舆情主题，例如：金融，银行等
 */
public class Topic {

	/* ID */
	private String id;

	/* 主题名称 */
	private String name;

	/* 必须有的关键词 */
	private String mustHave;

	/* 可以有的关键词 */
	private String mayHave;

	/* 不能有的关键词 */
	private String cannotHave;

	/* 是否负面 */
	private boolean isNegative;

	/* 开启报警 */
	private boolean enableWarning;

	/* 报警条件 */
	private int warningLimit;

	/* 报警方式 */
	private String warningType;

	/* 邮件标题or短信内容 */
	private String warningTitle;

	/* 禁用 */
	private boolean disable;
}

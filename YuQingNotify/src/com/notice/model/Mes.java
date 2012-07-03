package com.notice.model;

import java.io.Serializable;

/**
 * @author zhdwang
 * 
 */
public class Mes implements Serializable {
	private static final long serialVersionUID = 2049875287866199263L;
	//邮件标题
	private String title;
	//邮件内容
	private String content;
	//接受者的邮箱地址
	private String to;
	//使用什么协议发送，"email:"表示邮件
	private String protocal;

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the titile
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param titile
	 *            the titile to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return "Massage:" + "\n\r" + "to:" + to + "\n\r" + "titile:" + title
				+ "\n\r" + "content:" + content;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

}

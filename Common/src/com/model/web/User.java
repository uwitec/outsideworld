package com.model.web;

/*
 * 用户
 */
public class User {

	/* ID */
	private int id;

	private int groupId;

	private String name;

	private String email;

	private String phone;

	private String mobileNum;

	private String department;

	private String loginName;

	private String password;

	public static enum Role {
		NORMAL, MANAGER, SYSTEM
	}
}

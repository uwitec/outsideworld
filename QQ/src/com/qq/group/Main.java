package com.qq.group;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Login l = new Login();
		PollGroupMessage p = new PollGroupMessage(l.login("38348450", "zhdwangtravelsky"));
		p.start();

	}

}

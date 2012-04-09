package com.sohu.t.open.api;

import com.sohu.t.open.util.ConnectionClient;

public class Limit {
	
	/**
	 * 查看剩余访问次数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String limit() throws Exception {
		return ConnectionClient.doGetMethod("/account/rate_limit_status.json", "utf-8");
	}
	
	public static void main(String[] args) throws Exception {
		limit();
	}
}

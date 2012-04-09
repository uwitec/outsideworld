package com.sohu.t.open.api;

import com.sohu.t.open.util.ConnectionClient;

public class SocialGraph {

	/**
	 * 好友列表 
	 * @return
	 * @throws Exception
	 */
	public static String friends(String userId) throws Exception {
		return ConnectionClient.doGetMethod("/statuses/friends/"+userId+".json", "utf-8");
	}
	
	/**
	 * 粉丝列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	
	public static String followers(String userId) throws Exception {
		return ConnectionClient.doGetMethod("/statuses/followers/"+userId+".json", "utf-8");
	}
	
	
	public static void main(String[] args) throws Exception{
		SocialGraph.followers("87168679");
	}
}

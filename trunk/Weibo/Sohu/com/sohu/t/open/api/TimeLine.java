package com.sohu.t.open.api;

import com.sohu.t.open.util.ConnectionClient;

public class TimeLine {
	
	/**
	 * 获取用户timeline
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String getUserTimeLine(String userId) throws Exception{
		return ConnectionClient.doGetMethod("/statuses/user_timeline/"+userId+".json", "utf-8");
	}
	
	
	/**
	 * 获取@我的列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getMentionsTimeLine() throws Exception{
		return ConnectionClient.doGetMethod("/statuses/mentions_timeline.json", "utf-8");
	}
	
	public static void main(String[] args)  throws Exception {
		getMentionsTimeLine();
	}
}

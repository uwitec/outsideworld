package com.sohu.t.open.api;

import org.json.JSONObject;

import com.sohu.t.open.util.ConnectionClient;

public class Comment {
	
	/**
	 * 评论一条微博(回复评论)
	 * 
	 * @param comment 评论内容
	 * @param msgId  微博id
	 * @param reply_user_id 回复某人的uid
	 * @param is_transmit_enabled 是否转发
	 * @return
	 * @throws Exception
	 */
	public static String comment(String comment,String msgId,String reply_user_id,boolean is_transmit_enabled) throws Exception {
		JSONObject json = new JSONObject();
		json.put("id", msgId);
		json.put("comment", comment);
		json.put("reply_user_id", reply_user_id);
		json.put("is_transmit_enabled", is_transmit_enabled);
		return ConnectionClient.doPostMethod("/statuses/comment.json", json, "utf-8");
	}
	
	/**
	 * 评论一条微博
	 * 
	 * @param comment  评论内容
	 * @param msgId    微博id
	 * @param is_transmit_enabled 是否转发
	 * @return
	 * @throws Exception
	 */
	public static String comment(String comment,String msgId,boolean is_transmit_enabled) throws Exception {
		JSONObject json = new JSONObject();
		json.put("id", msgId);
		json.put("comment", comment);
		json.put("is_transmit_enabled", is_transmit_enabled);
		return ConnectionClient.doPostMethod("/statuses/comment.json", json, "utf-8");
	}
	
	
	
	/**
	 * 我收到的评论列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String comments_timeline() throws Exception {
		return ConnectionClient.doGetMethod("/statuses/comments_timeline.json", "utf-8");
	}
	
	
	
	public static void main(String[] args) throws Exception {
		comments_timeline();
    	//comment("测试评论并转发", "2464389947","38161379",false);
    }

}

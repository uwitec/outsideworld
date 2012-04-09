package com.sohu.t.open.api;

import org.json.JSONObject;

import com.sohu.t.open.util.ConnectionClient;

public class Mail {
	
	/**
	 * 获取私信列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getMailList() throws Exception {
		return ConnectionClient.doGetMethod("/direct_messages.json", "utf-8");
	}
	
	/**
	 * 
	 * @param mailId  微博id
	 * @param type  取值：in为收件箱，out为发件箱
	 * @return
	 * @throws Exception
	 */
	public static String destoryMail(String mailId,String type) throws Exception {
		return ConnectionClient.doDelMethod("/destroy/"+mailId+".json?type="+type, "utf-8");
	}
	
	/**
	 * 发送私信
	 * 
	 * @param user 用户的id
	 * @param text 私信内容
	 * @return
	 * @throws Exception
	 */
	public static String sendMail(String user,String text) throws Exception {
		JSONObject json = new JSONObject();
		json.put("user", user);
		json.put("text", text);
		return ConnectionClient.doPostMethod("/direct_messages/new.json", json, "utf-8");
	}
		
		
	public static void main(String[] args)  throws Exception {
		sendMail("87168679","你好");
		//destoryMail("41443091","in");
	}
}

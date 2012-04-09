package com.sohu.t.open.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.sohu.t.open.auth.SohuOAuth;
import com.sohu.t.open.util.ConnectionClient;

public class Relationship {
	
	
	/**
	 * 判断关注关系
	 * @param user_a
	 * @param user_b
	 * @return
	 * @throws Exception
	 */
	public static String isFocus(String user_a,String user_b) throws Exception {
		return ConnectionClient.doGetMethod("/friendships/exists.json?user_a="+user_a+"&user_b="+user_b, "utf-8");
	}
	
	/**
	 * 添加关注
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String createFocus(String userId) throws Exception {
		JSONObject json = new JSONObject();
		json.put("userId", userId);
		return ConnectionClient.doPostMethod("/friendships/create/"+userId+".json", json ,"utf-8");
		
	}
	
	public static void main(String[] args)  throws Exception {
		Relationship.isFocus("87168679","31681379");
	}
}

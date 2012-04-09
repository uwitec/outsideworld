package com.sohu.t.open.api;

import org.json.JSONObject;

import com.sohu.t.open.util.ConnectionClient;

public class User {
	/**
	 * 查看当前授权用户信息
	 * @return
	 * @throws Exception
	 */
	public static String show() throws Exception {
		return ConnectionClient.doGetMethod("/users/show.json", "utf-8");
	}
	
	/**
	 * 查看其它用户详细信息
	 * @param userId 用户id
	 * @return
	 * @throws Exception
	 */
	public static String show(String userId) throws Exception {
		return ConnectionClient.doGetMethod("/users/show/"+userId+".json", "utf-8");
	}
	
	
	/**
	 * 更新用户头像
	 * 
	 * @param localImagePath 本地头像地址
	 * @return
	 * @throws Exception
	 */
	public static String updateProfileImage(String localImagePath) throws Exception {
		return ConnectionClient.doPostMethod("/account/update_profile_image.json",null, "utf-8",localImagePath);
	}
	
	/**
	 * 更新用户信息
	 * 
	 * @param nickname
	 * @param email
	 * @param gender
	 * @param description
	 * @return
	 * @throws Exception
	 */
	public static String updateProfile(String nickname,String email,String gender,String description) throws Exception {
		JSONObject json = new JSONObject();
		if(description!=null){
			json.put("description", description);
		}
		if(email!=null){
			json.put("email", email);
		}
		if(nickname!=null){
			json.put("nickname", nickname);
		}
		if(gender!=null){
			json.put("gender", gender);
		}
		return ConnectionClient.doPostMethod("/account/update_profile.json", json, "utf-8");
	}
	
	
	public static void main(String[] args) throws Exception{
		//updateProfile(null,"dt_winsky@sohu.com",null,"滚滚长江水1");
		show("280340368");
	}
}

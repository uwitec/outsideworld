package com.sohu.t.open.api;

import org.json.JSONObject;

import com.sohu.t.open.util.ConnectionClient;


public class Status {
	
	/**
	 * 获取一条微博
	 * @param msgId
	 * @return
	 * @throws Exception
	 */
	public static String show(String msgId) throws Exception {
		return ConnectionClient.doGetMethod("/statuses/show/"+msgId+".json", "utf-8");
	}
	
	/**
	 * 发微博不带图
	 * @param status 微博内容
	 * @return
	 * @throws Exception
	 */
	public static String update(String status) throws Exception {
		JSONObject json = new JSONObject();
		json.put("status", status);
		return ConnectionClient.doPostMethod("/statuses/update.json", json, "utf-8");
	}
	
	
	/**
	 * 发微博带图片
	 * @param status
	 * @param geo  为空时不带经纬度信息，格式形如"经度,维度" ,  值形如 "108.95164,34.22083" 
	 * @param localFilePath
	 * @return
	 * @throws Exception
	 */
	public static String updateWithPic(String status,String localFilePath,String geo) throws Exception {
		JSONObject json = new JSONObject();
		json.put("status", status);
		if(geo!=null&&geo.indexOf(",")>0){
			json.put("longitude", geo.split(",")[0]);
			json.put("latitude",  geo.split(",")[1]);
		}
		return ConnectionClient.doPostMethod("/statuses/upload.json", json, "utf-8",localFilePath);
	}
	
	
	/**
	 * 发视频微博
	 * @param status
	 * @param localFilePath
	 * @return
	 * @throws Exception
	 */
	public static String updateWithVideo(String status,String geo,String videoUrl) throws Exception {
		JSONObject json = new JSONObject();
		json.put("status", status);
		if(geo!=null&&geo.indexOf(",")>0){
			json.put("longitude", geo.split(",")[0]);
			json.put("latitude",  geo.split(",")[1]);
		}
		json.put("videoUrl", videoUrl);
		return ConnectionClient.doPostMethod("/statuses/video.json", json, "utf-8");
	}
	
	
	/**
	 * 转发微博
	 * @param status
	 * @param msgId
	 * @return
	 * @throws Exception
	 */
	public static String transmit(String status,String msgId) throws Exception {
		JSONObject json = new JSONObject();
		json.put("status", status);
		return ConnectionClient.doPostMethod("/statuses/transmit/"+msgId+".json", json, "utf-8");
	}
	
	/**
	 * 批量获取微博的评论数和转发数，最多一次获取50个
	 * 
	 * @param ids 微博id,多个微博逗号分隔
	 * @return
	 * @throws Exception
	 */
	public static String counts(String ids) throws Exception {
		return ConnectionClient.doGetMethod("/statuses/counts.json?ids="+ids, "utf-8");
	}
	
	
	/**
	 * 获取最新消息提醒，包括“新粉丝数”、“新@提醒数”、“新评论数”、“新私信数”
	 * @return
	 * @throws Exception
	 */
	public static String check() throws Exception {
		return ConnectionClient.doGetMethod("/statuses/check.json", "utf-8");
	}
	
	
    public static void main(String[] args) throws Exception {
    	//check();
    	//update("1");
    	//updateWithVideo("好", null,"http://tv.sohu.com/20111212/n328661895.shtml");
    	updateWithPic("我们", "c://s.jpg",null);
    	//counts("2437043632,2437021752");
    	//show("2500205782");
    }
}

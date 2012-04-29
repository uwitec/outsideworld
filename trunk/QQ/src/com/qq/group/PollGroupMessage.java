package com.qq.group;

import java.util.Map;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONObject;

import com.model.Item;
import com.util.HttpUtil;

public class PollGroupMessage extends Thread {
	private Map<String,String> param;
	public PollGroupMessage(Map<String,String> param){
		this.param = param;
	}
	@Override
	public void run() {
		String pollUrl = "http://d.web2.qq.com/channel/poll2?clientid=" + param.get("clientid")
				+ "&psessionid=" + param.get("psessionid");
		while (true) {
			try {
				String refer = "http://d.web2.qq.com/proxy.html?v=20101025002";
				String ret = HttpUtil.doGet(pollUrl, "utf-8",refer);
				System.out.println(ret);
				JSONObject retJ = new JSONObject(ret);
				int retcode = retJ.getInt("retcode");
				if (retcode == 0) {
					JSONArray result = retJ.getJSONArray("result");
					String poll_type = result.getJSONObject(0).getString(
							"poll_type");
					JSONObject value = result.getJSONObject(0)
							.getJSONObject("value");
					if ("message".equals(poll_type)) {// 好友消息
					} else if ("buddies_status_change".equals(poll_type)) {// 好友上下线
					} else if ("group_message".equals(poll_type)) {// 群消息
						System.out.println(value);
					}
				} 
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("Response PollMessage failure = " + e.getMessage());
			}
			
		}
	}
	
	private Item  transToItem(String jsonR) {
		Item item = new Item();
		return item;
	}
}

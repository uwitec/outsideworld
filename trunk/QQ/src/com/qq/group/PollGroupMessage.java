package com.qq.group;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONObject;

import com.dao.ItemDao;
import com.model.Item;
import com.util.HttpUtil;

public class PollGroupMessage extends Thread {

	private Map<String, String> param;
	private ItemDao itemDao = Main.getBean("itemDao");
	private Map<String, Integer> groups;

	public PollGroupMessage(Map<String, String> param,
			Map<String, Integer> groups) {
		this.param = param;
		this.groups = groups;
	}

	@Override
	public void run() {
		Map<String, String> newParam = new HashMap<String, String>();
		String pollUrl = "http://d.web2.qq.com/channel/poll2";
		String clientid = param.get("clientid");
		String psessionid = param.get("psessionid");
		String r = "{\"clientid\":\"" + clientid + "\",\"psessionid\":\""
				+ psessionid + "\",\"key\":0,\"ids\":[]}";
		System.out.println(r);
		String refer = "http://d.web2.qq.com/proxy.html?v=20110331002&callback=2";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Referer", refer);
		try {
			r = URLEncoder.encode(r, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		newParam.put("r", r);
		newParam.put("clientid", clientid);
		newParam.put("psessionid", psessionid);
		while (true) {
			try {
				String ret = HttpUtil.doPost(pollUrl, "GBK", newParam,
						"application/x-www-form-urlencoded", headers);
				System.out.println(ret);
				JSONObject retJ = new JSONObject(ret);
				int retcode = retJ.getInt("retcode");
				if (retcode == 0) {
					JSONArray result = retJ.getJSONArray("result");
					String poll_type = result.getJSONObject(0).getString(
							"poll_type");
					JSONObject value = result.getJSONObject(0).getJSONObject(
							"value");
					if ("message".equals(poll_type)) {// 好友消息
					} else if ("buddies_status_change".equals(poll_type)) {// 好友上下线
					} else if ("group_message".equals(poll_type)) {// 群消息
						System.out.println(value);
						Item item = transToItem(value);
						if (item != null) {
							itemDao.insert(item);
						}
					}
				}
				Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println("Response PollMessage failure = "
						+ e.getMessage());
			}

		}
	}

	private Item transToItem(JSONObject jsonValue) throws Exception {
		String qqGroup = jsonValue.getString("info_seq");
		String sourceId = groups.get(qqGroup).toString();

		Item item = new Item();
		String content = jsonValue.getJSONArray("content").get(1).toString();
		item.setContent(content);
		item.setType("QQ");
		item.setSourceId(sourceId);
		item.setReplyNum(1);
		item.setCrawlTime(new Date());

		return item;
	}
}

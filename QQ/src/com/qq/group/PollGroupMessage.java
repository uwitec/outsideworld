package com.qq.group;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONObject;

import com.dao.ItemDao;
import com.model.Item;
import com.util.HttpUtil;

public class PollGroupMessage extends Thread {
	private static Logger LOG = Logger.getLogger(PollGroupMessage.class);
	private Map<String, String> param;
	private ItemDao itemDao = Main.getBean("itemDao");
	private Map<String, Integer> groups;
	private Login login;

	public PollGroupMessage(Map<String, String> param,
			Map<String, Integer> groups, Login l) {
		this.param = param;
		this.groups = groups;
		this.login = l;
	}

	@Override
	public void run() {
		while (true) {
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
					LOG.info("abtaining message...");
					String ret = HttpUtil.doPost(pollUrl, "GBK", newParam,
							"application/x-www-form-urlencoded", headers);
					System.out.println(ret);
					LOG.info("got message!message code is"+ret);
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
							LOG.info("Group message,message is:"+value);
							Item item = transToItem(value);
							if (item != null) {
								itemDao.insert(item);
							}
						}
					} else if (retcode == 108 || retcode == 121
							|| retcode == 114 || retcode == 122) {
						LOG.info("QQ begin to relogin....,the userName is"+this.param.get("userName"));
						this.param = login.login(this.param.get("userName"),
								this.param.get("password"));
						LOG.info("QQ relogin success!the userName is"+this.param.get("userName"));
						break;
					}
					LOG.info("QQ begin to sleeping...");
					Thread.sleep(2000);
					LOG.info("QQ woked!");
				} catch (Exception e) {
					System.out.println("Response PollMessage failure = "
							+ e.getMessage());
				}

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
		item.setCrawlTime(new Date());

		return item;
	}
}

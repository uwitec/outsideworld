package com.weibo;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.model.Item;
import com.tencent.weibo.utils.QHttpClient;

public class SinaWeiboClient extends AbstractWeiboClient<Map<String, Object>> {

	private QHttpClient httpClient = new QHttpClient();
	private String appKey = "1222837781";

	public static void main(String[] args) {
		new Thread(new SinaWeiboClient()).start();
	}

	@Override
	public void login() throws Exception {

	}

	@Override
	public Item wrapItem(Map<String, Object> weibo) {
		Item item = new Item();
		item.setContent(weibo.get("text").toString());
		return item;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getWeibos() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = null;
		String jsonStr = httpClient.httpGet(
				"http://api.t.sina.com.cn/statuses/public_timeline.json",
				"source=" + appKey + "&count=200&base_app=0");
		data = mapper.readValue(jsonStr, List.class);
		return data;
	}

	@Override
	public List<Item> filterItem(List<Item> weibos) {
		return weibos;
	}

	@Override
	public void saveItems(List<Item> items) throws Exception {
		System.out.println(items.size());
	}

	@Override
	public int getInterval() {
		return 0;
	}

	@Override
	public Object getIdentifier(Map<String, Object> weibo) {
		return weibo.get("mid");
	}

}

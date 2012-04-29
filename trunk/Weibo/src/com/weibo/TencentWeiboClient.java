package com.weibo;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.model.Item;
import com.tencent.weibo.api.Statuses_API;
import com.tencent.weibo.beans.OAuth;

public class TencentWeiboClient extends
		AbstractWeiboClient<Map<String, Object>> {

	private OAuth oauth;
	private Statuses_API st = new Statuses_API();

	@Override
	public void login() throws Exception {
		oauth = new OAuth("801106206", "030f769fcb345443df7f92ec4e711e1f",
				"null");
	}

	@Override
	public Object getIdentifier(Map<String, Object> weibo) {
		return weibo.get("id");
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
		String jsonStr = st.public_timeline(oauth, "json", "0", "100");
		Map<String, Object> m = (Map<String, Object>) mapper.readValue(jsonStr,
				Map.class).get("data");
		data = (List<Map<String, Object>>) m.get("info");
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

	public static void main(String[] args) throws Exception {
		new Thread(new TencentWeiboClient()).start();
	}
}

package com.weibo.client;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.codehaus.jackson.map.ObjectMapper;

import com.model.Item;
import com.tencent.weibo.api.Statuses_API;
import com.tencent.weibo.beans.OAuth;

public class TencentWeiboClient extends
		AbstractWeiboClient<Map<String, Object>> {

	private static Set<Serializable> cache = new HashSet<Serializable>(100);
	private static Lock lock = new ReentrantLock();

	private OAuth oauth;
	private Statuses_API st = new Statuses_API();

	public TencentWeiboClient(String[] params) {
		super(params);
	}

	@Override
	public void login() throws Exception {
		oauth = new OAuth(params[0], params[1], "null");
	}

	@Override
	public Serializable getIdentifier(Map<String, Object> weibo) {
		return weibo.get("id").toString();
	}

	@Override
	public Item wrapItem(Map<String, Object> weibo) {
		Item item = new Item();
		item.setUrl(weibo.get("id").toString());
		item.setSource("tencent");
		item.setContent(weibo.get("text").toString());
		item.setPubTime(new Date(1000 * Long.parseLong(weibo.get("timestamp")
				.toString())));
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
	public int getInterval() {
		return 0;
	}

	@Override
	public Set<Serializable> getCache() {
		return cache;
	}

	@Override
	public Lock getLock() {
		return lock;
	}
}

package com.weibo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.codehaus.jackson.map.ObjectMapper;

import com.model.Item;
import com.tencent.weibo.utils.QHttpClient;

public class SinaWeiboClient extends AbstractWeiboClient<Map<String, Object>> {

	private static Set<Serializable> cache = new HashSet<Serializable>(200);
	private static Lock lock = new ReentrantLock();

	private QHttpClient httpClient = new QHttpClient();

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"E MMM dd hh:mm:ss yyyy", Locale.US);

	public SinaWeiboClient(String[] params) {
		super(params);
	}

	@Override
	public void login() throws Exception {

	}

	@Override
	public Item wrapItem(Map<String, Object> weibo) {
		Item item = new Item();
		item.setContent(weibo.get("text").toString());
		try {
			item.setPubTime(sdf.parse(weibo.get("created_at").toString()
					.replace("+0800 ", "")));
		} catch (ParseException e) {
			e.printStackTrace();
			item.setPubTime(new Date());
		}
		return item;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getWeibos() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = null;
		String jsonStr = httpClient.httpGet(
				"http://api.t.sina.com.cn/statuses/public_timeline.json",
				"source=" + params[0] + "&count=200&base_app=0");
		data = mapper.readValue(jsonStr, List.class);
		return data;
	}

	@Override
	public int getInterval() {
		return 0;
	}

	@Override
	public Serializable getIdentifier(Map<String, Object> weibo) {
		return weibo.get("mid").toString();
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

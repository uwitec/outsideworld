package com.weibo.updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.sohu.t.open.util.ApiClient;
import com.weibo.NeedLoginException;

public class SinaWeiboUpdater extends AbstractWeiboUpdater {

	public SinaWeiboUpdater(String source, int chunk, String[] params) {
		super(source, chunk, params);
	}

	@Override
	public void login() throws Exception {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStatus(String ids) throws NeedLoginException,
			Exception {
		String jsonStr = ApiClient.doGetMethod(
				"http://api.t.sina.com.cn/statuses/counts.json?source="
						+ params[0] + "&ids=" + ids, "UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = null;
		data = mapper.readValue(jsonStr, List.class);
		List<Object[]> result = new ArrayList<Object[]>(data.size());
		Object[] obj = new Object[3];
		for (Map<String, Object> map : data) {
			obj[0] = map.get("id");
			obj[1] = map.get("comments");
			obj[2] = map.get("rt");
			result.add(obj);
		}
		return result;
	}
}

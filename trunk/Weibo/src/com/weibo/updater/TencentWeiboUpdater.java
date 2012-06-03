package com.weibo.updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;

import com.tencent.weibo.api.T_API;
import com.tencent.weibo.beans.OAuth;
import com.weibo.NeedLoginException;

public class TencentWeiboUpdater extends AbstractWeiboUpdater {

	private OAuth oauth;
	private T_API t = new T_API();

	public TencentWeiboUpdater(String source, int chunk, String[] params) {
		super(source, chunk, params);
	}

	@Override
	public void login() throws Exception {
		oauth = new OAuth(params[0], params[1], "null");
		oauth.setOauth_token(params[2]);
		oauth.setOauth_token_secret(params[3]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStatus(String ids) throws NeedLoginException,
			Exception {
		String jsonStr = t.re_count(oauth, "json", ids);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = (Map<String, Object>) mapper.readValue(
				jsonStr, Map.class).get("data");
		List<Object[]> result = new ArrayList<Object[]>(data.size());
		Object[] obj = new Object[3];
		Map<String, Object> info = null;
		for (Entry<String, Object> entry : data.entrySet()) {
			info = (Map<String, Object>) entry.getValue();
			obj[0] = entry.getKey();
			obj[1] = info.get("mcount");
			obj[2] = info.get("count");
			result.add(obj);
		}
		return result;
	}

}

package com.weibo;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class SohuWeiboClient extends AbstractWeiboClient<Map<String, String>> {

	@Override
	public boolean isSame(Map<String, String> weibo1, Map<String, String> weibo2) {
		if (StringUtils.equals(weibo2.get("id"), weibo2.get("id"))) {
			return true;
		}
		return false;
	}

	@Override
	public Item wrapItem(Map<String, String> weibo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, String>> getWeibos() throws Exception {

		String jsonObjs = http("http://api.t.sohu.com/statuses/public_timeline.json");
		if (StringUtils.isBlank(jsonObjs)
				|| StringUtils.startsWith(jsonObjs, "<html>")) {
			return null;
		}
		List<Map<String,String>> models = trans(jsonObjs);
		// 批量获取微薄的转发数和评论数
		String ids = "";
		for (Map<String, String> map : models) {
			ids += map.get("id") + ",";
		}
		if (ids.length() == 0) {
			return models;
		}
		ids = ids.substring(0, ids.length() - 1);
		jsonObjs = http("http://api.t.sohu.com/statuses/counts.json?ids=" + ids);
		List<Map<String,String>> transMap = trans(jsonObjs);
		for(Map<String,String> tMap:transMap){
			for(Map<String,String> mMap:models){
				if(StringUtils.equals(mMap.get("id"), tMap.get("id"))){
					mMap.put("comments_count", tMap.get("comments_count"));
					mMap.put("transmit_count", tMap.get("transmit_count"));
				}
			}
		}
		System.out.println(models);
		return models;
	}

	// @Override
	// public List<Item> filterItem(List<Item> weibos) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void saveItems(List<Item> items) throws Exception {
	//
	// }

	@Override
	public int getInterval() {
		return 10;
	}

	public static void main(String[] args) throws Exception {
		SohuWeiboClient c = new SohuWeiboClient();
		for (int i = 0; i < 60; i++) {
			c.getWeibos();
			Thread.sleep(5000);
		}
	}

	private String http(String url) throws Exception {
		HttpGet httpget = new HttpGet(url);
		HttpContext context = new BasicHttpContext();
		// 执行get方法
		HttpResponse response = HttpUtil.getHttpClient().execute(httpget,
				context);
		// 获得字符串
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "GBK");
	}
	
	private List<Map<String,String>> trans(String jsonObjs) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> models = mapper.readValue(jsonObjs,
				List.class);
		return models;
	}
}

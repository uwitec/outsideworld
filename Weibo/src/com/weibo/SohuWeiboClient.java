package com.weibo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.http.HttpParameters;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.model.Item;
import com.sohu.t.open.util.TwUtils;

public class SohuWeiboClient extends AbstractWeiboClient<Map<String, String>> {

	private String accessToken;
	private String accessTokenSecret;

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

		String jsonObjs = http(
				"http://api.t.sohu.com/statuses/public_timeline.json", "GET",
				false);
		if (StringUtils.isBlank(jsonObjs)
				|| StringUtils.startsWith(jsonObjs, "<html>")) {
			return null;
		}
		List<Map<String, String>> models = trans(jsonObjs);
		// 批量获取微薄的转发数和评论数
		String ids = "";
		for (Map<String, String> map : models) {
			ids += map.get("id") + ",";
		}
		if (ids.length() == 0) {
			return models;
		}
		ids = ids.substring(0, ids.length() - 1);
		jsonObjs = http(
				"http://api.t.sohu.com/statuses/counts.json?ids=" + ids, "GET",
				true);
		List<Map<String, String>> transMap = trans(jsonObjs);
		for (Map<String, String> tMap : transMap) {
			for (Map<String, String> mMap : models) {
				if (StringUtils.equals(mMap.get("id"), tMap.get("id"))) {
					mMap.put("comments_count", tMap.get("comments_count"));
					mMap.put("transmit_count", tMap.get("transmit_count"));
				}
			}
		}
		System.out.println(models);
		return models;
	}

	@Override
	public List<Item> filterItem(List<Item> weibos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveItems(List<Item> items) throws Exception {

	}

	@Override
	public int getInterval() {
		return 10;
	}

	public static void main(String[] args) throws Exception {
		SohuWeiboClient c = new SohuWeiboClient();
		String accessTokenStr = c.XAuthAuthorize("fangxia722@sohu.com",
				"fangxia722");
		if (StringUtils.isBlank(accessTokenStr)) {
			return;
		}
		c.setAccessToken(accessTokenStr.split("&")[0].split("=")[1]);
		c.setAccessTokenSecret(accessTokenStr.split("&")[1].split("=")[1]);
		for (int i = 0; i < 1; i++) {
			c.getWeibos();
			Thread.sleep(5000);
		}
	}

	private String http(String url, String method, boolean isAuthorize)
			throws Exception {
		URL u = new URL(url);
		HttpURLConnection request = (HttpURLConnection) u.openConnection();
		request.setDoOutput(true);
		Properties systemProperties = System.getProperties();
		systemProperties.setProperty("http.proxyHost", "172.17.18.80");
		systemProperties.setProperty("http.proxyPort", "8080");
		request.setRequestMethod(method);
		if (isAuthorize) {
			consumer.setTokenWithSecret(accessToken, accessTokenSecret);
			consumer.sign(request);
		}
		request.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				request.getInputStream(), "UTF-8"));
		String result = "";
		String b = null;
		while ((b = reader.readLine()) != null) {
			result += b;
		}
		request.disconnect();
		return result;
	}

	private List<Map<String, String>> trans(String jsonObjs) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> models = mapper.readValue(jsonObjs,
				List.class);
		return models;
	}

	private OAuthConsumer consumer = new DefaultOAuthConsumer(
			"ta9lM8nbMhHM8ZLQmsI8", "yftEFtbWH2N#MVZoW!^CdEu8RC*S!N1x8P6FFKo5");

	// xauth sign parameter
	public HttpURLConnection signRequestForXauth(HttpURLConnection request,
			String username, String password) throws Exception {
		HttpParameters para = new HttpParameters();
		para.put("x_auth_username", TwUtils.encode(username));
		para.put("x_auth_password", TwUtils.encode(password));
		para.put("x_auth_mode", "client_auth");
		consumer.setAdditionalParameters(para);
		consumer.sign(request);
		return request;
	}

	private String XAuthAuthorize(String username, String password)
			throws Exception {
		URL url = new URL("http://api.t.sohu.com/oauth/access_token");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.setDoOutput(true);
		request.setRequestMethod("POST");
		request = signRequestForXauth(request, username, password);
		Properties systemProperties = System.getProperties();
		systemProperties.setProperty("http.proxyHost", "172.17.18.80");
		systemProperties.setProperty("http.proxyPort", "8080");
		OutputStream ot = request.getOutputStream();
		ot.write(("x_auth_username=" + TwUtils.encode(username)
				+ "&x_auth_password=" + TwUtils.encode(password) + "&x_auth_mode=client_auth")
				.getBytes());
		ot.flush();
		ot.close();
		System.out.println("Sending request...");
		request.connect();
		System.out.println("Response: " + request.getResponseCode() + " "
				+ request.getResponseMessage());
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String token = "";
		String b = null;
		while ((b = reader.readLine()) != null) {
			token += b;
		}
		request.disconnect();
		return token;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	@Override
	public void login() throws Exception {
		String accessTokenStr = XAuthAuthorize("fangxia722@sohu.com",
				"fangxia722");
		if (StringUtils.isBlank(accessTokenStr)) {
			return;
		}
		setAccessToken(accessTokenStr.split("&")[0].split("=")[1]);
		setAccessTokenSecret(accessTokenStr.split("&")[1].split("=")[1]);
	}
}

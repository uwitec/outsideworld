package com.weibo.updater;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.http.HttpParameters;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.sohu.t.open.util.TwUtils;
import com.weibo.NeedLoginException;

public class SohuWeiboUpdater extends AbstractWeiboUpdater {

	private static Logger LOG = Logger.getLogger(SohuWeiboUpdater.class);

	private String accessToken;
	private String accessTokenSecret;

	public SohuWeiboUpdater(String source, int chunk, String[] params) {
		super(source, chunk, params);
	}

	private static Lock loginLock = new ReentrantLock();

	@Override
	public void login() throws Exception {
		try {
			loginLock.lock();
			String accessTokenStr = XAuthAuthorize(params[0], params[1]);
			if (StringUtils.isBlank(accessTokenStr)) {
				return;
			}
			setAccessToken(accessTokenStr.split("&")[0].split("=")[1]);
			setAccessTokenSecret(accessTokenStr.split("&")[1].split("=")[1]);

			consumer = new DefaultOAuthConsumer(params[2], params[3]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			loginLock.unlock();
		}
	}

	private synchronized String XAuthAuthorize(String username, String password)
			throws Exception {
		LOG.info("Try to login SohuWeibo");
		URL url = new URL("http://api.t.sohu.com/oauth/access_token");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.setDoOutput(true);
		request.setRequestMethod("POST");
		request = signRequestForXauth(request, username, password);
		OutputStream ot = request.getOutputStream();
		ot.write(("x_auth_username=" + TwUtils.encode(username)
				+ "&x_auth_password=" + TwUtils.encode(password) + "&x_auth_mode=client_auth")
				.getBytes());
		ot.flush();
		ot.close();
		request.connect();
		LOG.info("Response: " + request.getResponseCode() + " "
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

	private OAuthConsumer consumer = new DefaultOAuthConsumer(params[2],
			params[3]);

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

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStatus(String ids) throws NeedLoginException,
			Exception {
		if (ids.isEmpty()) {
			return new ArrayList<Object[]>();
		}
		LOG.info("Send Request to Sohuweibo");
		String jsonObjs = http(
				"http://api.t.sohu.com/statuses/counts.json?ids=" + ids, "GET",
				true);
		LOG.info("Get Response from Sohuweibo");
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = mapper.readValue(jsonObjs, List.class);
		Object[] obj = new Object[3];
		List<Object[]> result = new ArrayList<Object[]>(data.size());
		LOG.info("Retrieve " + data.size() + " status from Sohuweibo");
		for (Map<String, Object> map : data) {
			obj[0] = map.get("id");
			obj[1] = map.get("comments_count");
			obj[2] = map.get("transmit_count");
			result.add(obj);
		}
		return result;
	}

	private String http(String url, String method, boolean isAuthorize)
			throws Exception {
		URL u = new URL(url);
		HttpURLConnection request = (HttpURLConnection) u.openConnection();
		request.setDoOutput(true);
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
}

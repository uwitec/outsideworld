package com.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	private static HttpClient httpClient;
	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 800;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 600000;

	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 100000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 100000;

	static {
		HttpParams httpParams = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(httpParams,
				MAX_TOTAL_CONNECTIONS);
		// 设置获取连接的最大等待时间
		ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
		// 设置连接超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
		// 设置读取超时时间
		HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);

		// 设置coolies
		HttpClientParams.setCookiePolicy(httpParams,
				CookiePolicy.BROWSER_COMPATIBILITY);
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		registry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		ClientConnectionManager cm = new ThreadSafeClientConnManager(
				httpParams, registry);
		httpClient = new DefaultHttpClient(cm, httpParams);
	}

	public static HttpClient getHttpClient() {
		return httpClient;
	}

	public static String doGet(String url, String encoding,String Refer) throws Exception {
		HttpGet httpget = new HttpGet(url);
		if (!StringUtils.isBlank(Refer)) {
			httpget.setHeader("Referer", Refer);
		}
		HttpContext context = new BasicHttpContext();
		String result = null;
		try {
			// 执行get方法
			HttpResponse response = httpClient.execute(httpget, context);
			// 获得字符串
			HttpEntity entity = response.getEntity();
			byte[] bytes = EntityUtils.toByteArray(entity);
			if (entity != null) {
				result = new String(bytes, encoding);
				entity.consumeContent();
			}

		} catch (Exception e) {
			httpget.abort();
			e.printStackTrace();
		}
		return result;
	}

	public static String doPost(String url, String encoding,
			Map<String, Object> param, String Refer) throws Exception {
		HttpPost httpget = new HttpPost(url);

		// 构造最简单的字符串数据
		String paramStr = "";
		if (param != null) {
			for (Map.Entry<String, Object> p : param.entrySet()) {
				paramStr += p.getKey() + "=" + p.getValue() + "&";
			}
			StringEntity reqEntity = new StringEntity(paramStr);
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			httpget.setEntity(reqEntity);
			
		}
		if (!StringUtils.isBlank(Refer)) {
			httpget.setHeader("Referer", Refer);
		}

		HttpContext context = new BasicHttpContext();
		String result = null;
		try {
			// 执行get方法
			HttpResponse response = httpClient.execute(httpget, context);
			// 获得字符串
			HttpEntity entity = response.getEntity();
			byte[] bytes = EntityUtils.toByteArray(entity);
			if (entity != null) {
				result = new String(bytes, encoding);
				entity.consumeContent();
			}

		} catch (Exception e) {
			httpget.abort();
			e.printStackTrace();
		}
		return result;
	}
}
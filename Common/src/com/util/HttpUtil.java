package com.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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

@SuppressWarnings("deprecation")
public class HttpUtil {
	private static DefaultHttpClient httpClient;
	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 800;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 60000;

	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 60000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 60000;

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

	public static DefaultHttpClient getHttpClient() {
		return httpClient;
	}

	public static String doGet(String url, String encoding,
			Map<String, String> headers) throws Exception {
		String result = null;
		HttpGet httpget = null;
		try {
			httpget = new HttpGet(url);
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> header : headers.entrySet()) {
					httpget.setHeader(header.getKey(), header.getValue());
				}
			}
			HttpContext context = new BasicHttpContext();
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
			throw new Exception(e);
		} finally {
			httpget.abort();
		}
		return result;
	}

	public static String doPost(String url, String encoding,
			Map<String, String> param, String contentType,
			Map<String, String> headers) throws Exception {
		String result = null;
		HttpPost httpget = null;
		try {
			httpget = new HttpPost(url);
			// 构造最简单的字符串数据
			String paramStr = "";
			if (param != null) {
				for (Map.Entry<String, String> p : param.entrySet()) {
					paramStr += p.getKey() + "=" + p.getValue() + "&";
				}
				StringEntity reqEntity = new StringEntity(paramStr);
				// 设置类型
				if (!StringUtils.isBlank(contentType)) {
					reqEntity.setContentType(contentType);
				}
				httpget.setEntity(reqEntity);

			}
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> header : headers.entrySet()) {
					httpget.setHeader(header.getKey(), header.getValue());
				}
			}
			HttpContext context = new BasicHttpContext();
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
			throw new Exception(e);
		} finally {
			httpget.abort();
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String url = "http://www.ttcnn.com/../..//vector/img/vector7/335/072.jpg";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; rv:13.0) Gecko/20100101 Firefox/13.0.1");
		headers.put("Cookie",
				"Hm_lvt_23d1669e4d1c9400abc9e6d1f880d35a=1340976557090");
		headers.put("Referer", "http://www.ttcnn.com");
		headers.put("Connection", "keep-alive");
		headers.put("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		headers.put("Cache-Control", "	no-cache");
		headers.put("Host", "www.ttcnn.com");
		headers.put("Pragma", "no-cache");
		HttpUtil.doGet(url, "GBK", headers);
	}
}

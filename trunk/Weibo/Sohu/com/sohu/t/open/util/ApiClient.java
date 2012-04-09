package com.sohu.t.open.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;

import com.sohu.t.open.auth.SohuOAuth;

/**
 * @author georgecao
 */
public class ApiClient {
	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static int TIMEOUT = 10 * 1000;
	static int MAX_HTTP_CONNECTION = 50;
	static int count = 0;

	public static void main(String[] args) throws Exception {

	}

	public static String doPostMethod(String url, JSONObject json, String encoding) {
		String ret = "";

		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Encoding", "text/html");
		postMethod.setRequestHeader("Content-Type",	"application/x-www-form-urlencoded;charset=" + encoding);
		postMethod.setRequestHeader("Connection", "closed");
		try {
			Iterator iter = json.keys();
			List<NameValuePair> nvList = new ArrayList<NameValuePair>();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				nvList.add(new NameValuePair(key, json.getString(key)));
			}

			postMethod.setRequestBody(nvList.toArray(new NameValuePair[] {}));
			httpClient.executeMethod(postMethod);

			ret = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			System.err.println("TwitterHttpClient:postNew failed.");
		} finally {
			postMethod.releaseConnection();
		}

		return ret;
	}

	
	public static String doGetMethod(String url,String encoding) {
		String ret = "";

		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		GetMethod getMethod = new GetMethod(url);

		getMethod.setRequestHeader("Content-Encoding", "text/html");
		getMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		//getMethod.setRequestHeader("Connection", "keep-alive");
		//SohuOAuth.signRequestGet(httpClient.getHttpConnectionManager().getConnection(new HostConfiguration()));
		try {
			httpClient.executeMethod(getMethod);
			ret = getMethod.getResponseBodyAsString();

		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			getMethod.releaseConnection();
		}
		return ret;
	}

}

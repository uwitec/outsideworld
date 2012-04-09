package com.sohu.t.open.auth;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.http.HttpParameters;

import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONObject;

import com.sohu.t.open.util.TwUtils;

public class SohuOAuth {
	public static String host = "";
	private static String consumerKey= "";
	private static String consumerSecret = "";
	private static String accessToken= "";
	private static String tokenSecret = "";
	public static String oauth_callback = "";
	
	//for xauth
	public static String username = "";
	public static String pwd = "";
	
	
	static {
		Properties props = new Properties();
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		String path = (url + "auth.property").substring(6);
		System.out.println("---------auth.property path is " + path + "--------- ");
        InputStream in = null ;
		try {
			in = new BufferedInputStream (new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			props.load(in);
			consumerKey = props.getProperty("consumerKey").trim();
			consumerSecret = props.getProperty("consumerSecret").trim();
			accessToken = props.getProperty("accessToken").trim();
			tokenSecret = props.getProperty("tokenSecret").trim();
			oauth_callback = props.getProperty("oauth_callback").trim();
			username = props.getProperty("username").trim();
			pwd = props.getProperty("pwd").trim();
			host = props.getProperty("host").trim();
			System.out.println("---------host            = " + host + "--------- ");
			System.out.println("---------consumerKey    = " + consumerKey + "--------- ");
			System.out.println("---------consumerSecret = " + consumerSecret + "--------- ");
			System.out.println("---------accessToken    = " + accessToken + "--------- ");
			System.out.println("---------tokenSecret    = " + tokenSecret + "--------- ");
			System.out.println("---------oauth_callback = " + oauth_callback + "--------- ");
			System.out.println("---------username       = " + username + "--------- ");
			System.out.println("---------pwd            = " + pwd + "--------- ");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  static OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey,consumerSecret);
	
	
	
	
	
	
	
	
	//oauth sign POST request 
	public static HttpURLConnection signRequestPost(HttpURLConnection request,JSONObject json) throws Exception {
		consumer.setTokenWithSecret(accessToken,tokenSecret);
		return sign(request,json);
		
	}
	
	//oauth sign GET request
	public static HttpURLConnection signRequestGet(HttpURLConnection request) throws Exception {
		consumer.setTokenWithSecret(accessToken,tokenSecret);
        consumer.sign(request);
		return request;
		
	}
	
	//xauth sign parameter
	public static HttpURLConnection signRequestForXauth(HttpURLConnection request) throws Exception {
		HttpParameters para = new HttpParameters();
		para.put("x_auth_username", TwUtils.encode(username));
	  	para.put("x_auth_password", TwUtils.encode(pwd));
	  	para.put("x_auth_mode", "client_auth");	
	  	consumer.setAdditionalParameters(para);
        consumer.sign(request);
        return request;
	}
	
	
	public static HttpURLConnection sign(HttpURLConnection request,JSONObject json) throws Exception {
		if(json!=null){
			HttpParameters para = new HttpParameters();
			Iterator iter = json.keys();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				para.put(key,TwUtils.escape(TwUtils.encode(json.getString(key)==null?"":json.getString(key))));
			}
	    	consumer.setAdditionalParameters(para);
		}
        consumer.sign(request);
        return request;
	}
}

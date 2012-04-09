package com.sohu.t.open.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.sohu.t.open.util.TwUtils;


import oauth.signpost.OAuth;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;

public class Authorize {

	//xauth 获取access_token
	public static void XAuthAuthorize() throws Exception {
		URL url = new URL("http://api.t.sohu.com/oauth/access_token");
  		HttpURLConnection request = (HttpURLConnection) url.openConnection();
	  	request.setDoOutput(true);
	  	request.setRequestMethod("POST");
	  	
	  	request = SohuOAuth.signRequestForXauth(request);
	  	OutputStream ot = request.getOutputStream();
      	ot.write(("x_auth_username="+TwUtils.encode(SohuOAuth.username)+"&x_auth_password="+TwUtils.encode(SohuOAuth.pwd)+"&x_auth_mode=client_auth").getBytes());
      	ot.flush();
      	ot.close();
      	System.out.println("Sending request...");
      	request.connect();
  		System.out.println("Response: " + request.getResponseCode() + " "
  			+ request.getResponseMessage());
		BufferedReader reader =new BufferedReader(new InputStreamReader(request.getInputStream()));
		String b = null;
		while((b = reader.readLine())!=null){
			System.out.println(b);
		}
		request.disconnect();
	}
	
	//oauth 授权 获取access_token
	public static void OAuthAuthorize() throws Exception {	  
       OAuthProvider provider = new DefaultOAuthProvider("http://127.0.0.1/oauth/request_token",
                "http://api.t.sohu.com/oauth/access_token",
                "http://api.t.sohu.com/oauth/authorize?hd=default");
       
        System.out.println("第一步，根据注册时生成的appkey,appsecret取request token");
        String authUrl = provider.retrieveRequestToken(SohuOAuth.consumer,SohuOAuth.oauth_callback);
        System.out.println("Request token: " + SohuOAuth.consumer.getToken());
        System.out.println("Token secret: " + SohuOAuth.consumer.getTokenSecret());

        System.out.println("第二步，通过取得的request token让用户授权，拷贝此地址到浏览器" + authUrl);
        System.out.println("第三步，通过授权后浏览器会返回一个code，把这个code输入System.in中");
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String verificationCode = br.readLine();
        System.out.println("verificationCode="+verificationCode);
        System.out.println("Fetching access token...");
        provider.retrieveAccessToken(SohuOAuth.consumer,verificationCode.trim());
        System.out.println("Access token: " + SohuOAuth.consumer.getToken());
        System.out.println("Token secret: " + SohuOAuth.consumer.getTokenSecret());
        
        System.out.println("第四步，成功获得Access Token");
	}
	
	
	
	
    public static void main(String[] args) throws Exception {
    	Authorize.OAuthAuthorize();
    }
}

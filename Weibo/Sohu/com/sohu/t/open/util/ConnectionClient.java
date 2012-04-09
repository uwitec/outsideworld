package com.sohu.t.open.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONObject;

import com.sohu.t.open.auth.SohuOAuth;

public class ConnectionClient {
	
	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doGetMethod(String url, String encoding) throws Exception {
		URL urlConn = new URL(SohuOAuth.host+url);
        HttpURLConnection request = (HttpURLConnection) urlConn.openConnection();
        SohuOAuth.signRequestGet(request);
        System.out.println("Sending request...");
        request.connect();
        System.out.println("Response: " + request.getResponseCode() + " "  + request.getResponseMessage());
        BufferedReader reader =new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
		String ret = null;
		while((ret = reader.readLine())!=null){
			System.out.println(ret);
		}
		return ret;
	}
	
	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param json
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doPostMethod(String url, JSONObject json, String encoding) throws Exception {
    	URL urlConn = new URL(SohuOAuth.host+url);
    	HttpURLConnection request = (HttpURLConnection) urlConn.openConnection();
    	request.setDoOutput(true);
    	request.setRequestMethod("POST");
    	
    	//oauth sign
    	request = SohuOAuth.signRequestPost(request,json);
    	
        OutputStream ot = request.getOutputStream();
        
        if(json!=null){
	        Iterator iter = json.keys();
	        int i = 0 ;
			while (iter.hasNext()) {
				String linkNote = "";
				String key = (String) iter.next();
				if(i>0){
					linkNote = "&";
				}
				ot.write((linkNote+key+"="+TwUtils.encode(json.getString(key),encoding)).getBytes());
				i++;
			}
        }
        ot.flush();
        ot.close();
    	System.out.println("Sending request...");
    	request.connect();
    	System.out.println("Response: " + request.getResponseCode() + " " + request.getResponseMessage());
		BufferedReader reader =new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
		String b = null;
		while((b = reader.readLine())!=null){
			System.out.println(b);
		}
		return b;
	}
	
	
	/**
	 * 发送post带图片的请求
	 * @param url
	 * @param json
	 * @param encoding
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	
	public static String doPostMethod(String url, JSONObject json, String encoding,String filePath) throws Exception {
		String name = "pic";
		if(url.indexOf("update_profile_image")>0){
			name = "image";
		}
		File f = new File(filePath);
    	URL connUrl = new URL(SohuOAuth.host+url);
    	HttpURLConnection request = (HttpURLConnection) connUrl.openConnection();
    	request.setDoOutput(true);
    	request.setRequestMethod("POST");
    	
    	request = SohuOAuth.signRequestPost(request,json);
        
    	String boundary = "---------------------------37531613912423";
    	
    	request.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary); //设置表单类型和分隔符
    	
		String pic = "\r\nContent-Disposition: form-data; name=\""+name+"\"; filename=\"postpic.jpg\"\r\nContent-Type: image/jpeg\r\n\r\n";
    	byte[] end_data = ("\r\n--" + boundary + "--\r\n").getBytes();  
		FileInputStream stream = new FileInputStream(f);
		byte[] file = new byte[(int)f.length()];
		stream.read(file);
        
        OutputStream ot = request.getOutputStream();
        ot.write(("\r\n--"+boundary).getBytes());
        if(json!=null){
	        Iterator iter = json.keys();
	        int i = 0 ;
			while (iter.hasNext()) {
				String key = (String) iter.next();				
		        ot.write(contentType(key).getBytes());
		        ot.write(TwUtils.encode(json.getString(key),encoding).getBytes());
		        ot.write(("\r\n--"+boundary).getBytes());
			}
        }
        ot.write(pic.getBytes());
        ot.write(file);
        ot.write(end_data);
        ot.flush();
        ot.close();
    	System.out.println("Sending request...");
    	request.connect();
    	System.out.println("Response: " + request.getResponseCode() + " "
    			+ request.getResponseMessage());
		BufferedReader reader =new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
		String b = null;
		while((b = reader.readLine())!=null){
			System.out.println(b);
		}
		return b;
	}

	/**
	 * 发送delete请求
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doDelMethod(String url, String encoding) throws Exception { 
		URL urlConn = new URL(SohuOAuth.host+url);
        HttpURLConnection request = (HttpURLConnection) urlConn.openConnection();
        request.setRequestMethod("DELETE");
        SohuOAuth.signRequestGet(request);
        System.out.println("Sending request...");
        request.connect();
        System.out.println("Response: " + request.getResponseCode() + " "  + request.getResponseMessage());
        BufferedReader reader =new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
		String ret = null;
		while((ret = reader.readLine())!=null){
			System.out.println(ret);
		}
		return ret;
	}
	
	
	private static String contentType(String key) {
		return  "\r\nContent-Disposition: form-data; name=\""+key+"\"\r\n\r\n";
	}
	
}

package com.qq.group;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;

import atg.taglib.json.util.JSONObject;

import com.util.HttpUtil;
import com.util.MD5;

public class Login {
	private MD5 md = new MD5();
	private int clientid = 35599910;
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
    public Map<String,String> login(String userName,String password) throws Exception{
    	Map<String,String> resultMap = new HashMap<String,String>();
    	resultMap.put("clientid", String.valueOf(clientid));
    	String check = abtainedVerify(userName);
    	String loginUrl = "http://ptlogin2.qq.com/login?u="
				+ userName
				+ "&"
				+ "p="
				+ md.calcMD5(md.calcMD5_3(password) + check)
				+ "&verifycode="
				+ check
				+ "&remember_uin=1&aid=1003903"
				+ "&u1=http%3A%2F%2Fweb2.qq.com%2Floginproxy.html%3Fstrong%3Dtrue"
				+ "&h=1&ptredirect=0&ptlang=2052&from_ui=1&pttype=1&dumy=&fp=loginerroralert";
        String result = HttpUtil.doGet(loginUrl, "utf-8",null);
        System.out.println(result);
        Pattern p = Pattern.compile("登录成功！");
		Matcher m = p.matcher(result);
		if (!m.find()) {
			result = "";
		} 
		List<Cookie> cookies = ((AbstractHttpClient)HttpUtil.getHttpClient()).getCookieStore().getCookies();
		for(Cookie c:cookies){
			System.out.println(c);
			if(StringUtils.equals("ptwebqq", c.getName())){
				resultMap.put(c.getName(), c.getValue());
				String jsonR = loginAfter(c.getValue());
				//从jsonR中获取"vfwebqq""psessionid"
				JSONObject value = new JSONObject(jsonR);
				if(StringUtils.equals("0",value.getString("retcode"))){
					JSONObject o =value.getJSONObject("result");
					resultMap.put("vfwebqq",o.getString("vfwebqq"));
					resultMap.put("psessionid", o.getString("psessionid"));
				}
				
			}
			if(StringUtils.equals("skey", c.getName())){
				resultMap.put(c.getName(), c.getValue());
			}
			
		}
		
    	return resultMap;
    }
    /**
     * 看是否需要验证码，大多数情况下不需要，因此也就没有针对验证吗做处理
     * @param userName
     * @return
     * @throws Exception
     */
    private String abtainedVerify(String userName) throws Exception {
    	String url = "http://ptlogin2.qq.com/check?appid=1003903&uin="+userName;
    	String result = HttpUtil.doGet(url, "utf-8",null);
    	Pattern p = Pattern.compile("\\,\\'([!\\w]+)\\'");
		Matcher m = p.matcher(result);
		if (m.find()) {
			result = m.group(1);
		}
		System.out.println(result);
    	return result;
    }
    
    private String loginAfter(String ptWebQQ) throws Exception {
    	
    	String refer = "http://d.web2.qq.com/proxy.html?v=20101025002";
		String channelLoginUrl = "http://d.web2.qq.com/channel/login2";
		String content = "{\"status\":\"\",\"ptwebqq\":"+"\""+ptWebQQ+"\",\"passwd_sig\":\"\",\"clientid\":\""+clientid+"\"}";
		System.out.println(content);
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("r", content);
		String result = HttpUtil.doPost(channelLoginUrl, "utf-8", params,refer);
		System.out.println(result);
		return result;

    }
    
    public static void main(String[] args) throws Exception{
    	Login login = new Login();
    	login.login("38348450","zhdwangtravelsky");
    }
}

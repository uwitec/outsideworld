package com.qq.group;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.apache.commons.lang.StringUtils;
import org.apache.http.cookie.Cookie;
import atg.taglib.json.util.JSONObject;
import com.util.HttpUtil;

public class Login {
	private int clientid = 35599910;
	private static final String appid="1003903";

	/**
	 * 登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> login(String userName, String password)
			throws Exception {
		//第一步骤，先进行verify的检验，查看是否需要输入验证码
		String[] checks = abtainedVerify(userName);
		//登录的url，每次需要加入一个随机数字才能登录
		String loginUrl = "http://ptlogin2.qq.com/login?u="
				+ userName
				+ "&"
				+ "p="
				//+ md.calcMD5(md.calcMD5_3(password) + checks[1])
				+getPassWord(password,checks[1],checks[2])
				+ "&verifycode="
				+ checks[1]
				+ "&webqq_type=10&remember_uin=1&login2qq=1"
				+"&aid="+appid
				+"&u1=http%3A%2F%2Fweb.qq.com%2Floginproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&h=1&ptredirect=0&ptlang=2052&from_ui=1&pttype=1&dumy=&fp=loginerroralert&mibao_css=m_webqq&g=1&t=1&dummy=";
		//loginUrl = loginUrl+new Date().getTime();
		//登录的header
		Map<String,String> header = new HashMap<String,String>();
		header.put("Connection", "keep-alive");
		header.put("Host", "ptlogin2.qq.com");
		header.put("User-Agent",
                "Mozilla/5.0 (Windows NT 5.1; rv:13.0) Gecko/20100101 Firefox/13.0");
		header.put("Referer", "http://t.qq.com/?from=11");
		String result = HttpUtil.doGet(loginUrl, "utf-8", header);
		System.out.println(result);
		Pattern p = Pattern.compile("登录成功！");
		Matcher m = p.matcher(result);
		if (!m.find()) {
			result = "";
		}
		Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("clientid", String.valueOf(clientid));
		List<Cookie> cookies = HttpUtil.getHttpClient().getCookieStore()
				.getCookies();
		for (Cookie c : cookies) {
			System.out.println(c);
			if (StringUtils.equals("ptwebqq", c.getName())) {
				resultMap.put(c.getName(), c.getValue());
				String jsonR = loginAfter(c.getValue());
				// 从jsonR中获取"vfwebqq""psessionid"
				JSONObject value = new JSONObject(jsonR);
				if (StringUtils.equals("0", value.getString("retcode"))) {
					JSONObject o = value.getJSONObject("result");
					resultMap.put("vfwebqq", o.getString("vfwebqq"));
					resultMap.put("psessionid", o.getString("psessionid"));
				}

			}
			if (StringUtils.equals("skey", c.getName())) {
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
	private String[] abtainedVerify(String userName) throws Exception {
		String url = "http://check.ptlogin2.qq.com/check?appid="+appid+"&uin="
				+ userName+"&plang=2052&r=" + Math.random();
		//设置header
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:13.0) Gecko/20100101 Firefox/13.0");
		headers.put("Host", "check.ptlogin2.qq.com");
		headers.put("Referer", "http://t.qq.com/?from=11");
		String result = HttpUtil.doGet(url, "utf-8", headers);
		String[] checkNum = result.substring(result.indexOf("(") + 1,
		        result.lastIndexOf(")")).replace("'", "").split(",");
		return checkNum;
	}

	private String loginAfter(String ptWebQQ) throws Exception {
		String refer = "http://d.web2.qq.com/proxy.html?v=20110331002&callback=2";
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Referer", refer);
		String channelLoginUrl = "http://d.web2.qq.com/channel/login2";
		String content = "{\"status\":\"online\",\"ptwebqq\":" + "\"" + ptWebQQ
				+ "\",\"passwd_sig\":\"\",\"clientid\":\"" + clientid + "\",\"psessionid\":\"null\"}";
		System.out.println(content);
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("r", content);
		String result = HttpUtil
				.doPost(channelLoginUrl, "utf-8", params, headers);
		System.out.println(result);
		return result;

	}
	
	private String getPassWord(String p,String checks1,String checks2)throws Exception{
	    ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        String jsFileName = "src/md5.js"; // 指定md5加密文件
        // 读取js文件
        FileReader reader;

        reader = new FileReader(jsFileName);
        engine.eval(reader);
        String pass = "";
        if (engine instanceof Invocable) {
            Invocable invoke = (Invocable) engine;
            // 调用preprocess方法，并传入两个参数密码和验证码

            pass = invoke.invokeFunction("QXWEncodePwd",
                    checks2.trim(), p, checks1.trim()).toString();
            System.out.println("c = " + pass);
        }
        reader.close();
        return pass;
	}

	public static void main(String[] args) throws Exception {
		Login login = new Login();
		login.login("38348450", "zhdwangtravelsky");
	}
}

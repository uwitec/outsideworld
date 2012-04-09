package demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;

import weibo4j.model.MySSLSocketFactory;

public class Auth4Code {
	public static void main(String[] args) {
		Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		String result = null;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				"https://api.t.sina.com.cn/oauth2/authorize");
		postMethod.addParameter("client_id", "1222837781"); // appkey
		postMethod.addParameter("redirect_uri", ""); // oauth2 回调地址
		postMethod.addParameter("response_type", "code");
		postMethod.addParameter("action", "submit");
		postMethod.addParameter("userId", "aries_monster@163.com"); // 微博帐号
		postMethod.addParameter("passwd", "heroiam"); // 帐号密码
		try {
			client.executeMethod(postMethod);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = postMethod.getResponseHeader("location").getValue();
		String params = url.substring(url.lastIndexOf("?") + 1);
		Map<String, String> paramsMap = new HashMap<String, String>();
		for (String s : params.split("&")) {
			String[] t = s.split("=");
			paramsMap.put(t[0], t[1]);
		}
		String code = paramsMap.get("code");
		System.out.println(code);
		PostMethod tokenMethod = new PostMethod(
				"https://api.t.sina.com.cn/oauth2/access_token");
		tokenMethod.addParameter("client_id", "1222837781"); // appkey
		tokenMethod.addParameter("client_secret",
				"ef037b77fef877f12b01dd227ca61aff"); // appsecret
		tokenMethod.addParameter("grant_type", "authorization_code");
		tokenMethod.addParameter("code", code); // 上一步骤拿到的code
		tokenMethod.addParameter("redirect_uri", ""); // 回调地址
		try {
			client.executeMethod(tokenMethod);
			result = Thread.currentThread().getName() + "--->"
					+ tokenMethod.getResponseBodyAsString();
			System.out.println(result);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String token = result.split(",")[0].split("\"")[3];
		System.out.println(token);
	}
}

package com.weibo;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tencent.weibo.utils.OAuthClient;
import com.tencent.weibo.utils.QHttpClient;

public class TencentWeiboClient{
	
    public void login()  throws Exception {
		com.tencent.weibo.beans.OAuth oauth = new com.tencent.weibo.beans.OAuth(
				"801106206", "030f769fcb345443df7f92ec4e711e1f",
				"null");
		OAuthClient auth = new OAuthClient();

		// 获取request token
		oauth = auth.requestToken(oauth);

		if (oauth.getStatus() == 1) {
			System.out.println("Get Request Token failed!");
			return;
		} else {
			String oauth_token = oauth.getOauth_token();

			String url = "http://open.t.qq.com/cgi-bin/authorize?oauth_token="
					+ oauth_token+"&checkStatus=no&checkType=login";

			System.out.println("Get verification code......url:"+url);
			//使用httpUnit
			WebClient webClient = new WebClient();
			webClient.setJavaScriptEnabled(false);
			webClient.setCssEnabled(false);
			HtmlPage page = webClient.getPage(url);
			//点击“授权”按钮，获取授权页面
			HtmlElement button = null;
			List<HtmlElement> list = page.getElementsByTagName("input");
			for (HtmlElement a : list) {
				if ("sub".equals(a.getAttribute("class"))&&"submit".equals(a.getAttribute("type"))) {
					button = a;
					break;
				}
			}
			HtmlElement loginname = page.getElementById("u");
			HtmlElement password = page.getElementById("p");
			loginname.setAttribute("value", "1808318464");
			password.setAttribute("value", "20110528wj");
			HtmlPage loginPage = button.click();
			/* get token from URL */
			HtmlElement vCode = loginPage.getElementById("vCode");

			/* close windows */

			webClient.closeAllWindows();

			/* return token */
			String vcode = vCode.getAttribute("text");
			System.out.println("当前授权吗是："+vcode);
			oauth.setOauth_verifier(vcode);
			oauth = auth.accessToken(oauth);
			System.out.println("Response from server：");

			if (oauth.getStatus() == 2) {
				System.out.println("Get Access Token failed!");
				return;
			} else {
				
			}
		}
    }
    
    public static void main(String[] args) throws Exception{
    	TencentWeiboClient client = new TencentWeiboClient();
    	client.login();
    }
}

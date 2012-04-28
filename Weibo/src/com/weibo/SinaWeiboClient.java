package com.weibo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.http.AccessToken;
import weibo4j.model.Status;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.model.Item;

public class SinaWeiboClient extends AbstractWeiboClient<Status> {

	private Weibo weibo = new Weibo();
	private Timeline tm = new Timeline();
	private AccessToken token = null;

	private void getToken() throws Exception {
		WebClient webClient = null;
		Oauth oauth = new Oauth();
		HtmlPage page = null;
		HtmlPage loginPage = null;
		HtmlPage authPage = null;
		try {
			webClient = new WebClient();
			webClient.waitForBackgroundJavaScript(50);
			webClient.setRedirectEnabled(true);
			webClient.setThrowExceptionOnScriptError(false);
			webClient.setCssEnabled(false);

			// OAuth页面
			page = webClient.getPage(oauth.authorize("code"));
			String url = null;

			// 填写表单
			HtmlElement loginname = page.getElementById("userId");
			HtmlElement password = page.getElementById("passwd");
			loginname.setAttribute("value", "sinaweibot@126.com");
			password.setAttribute("value", "5131421");

			// 提交表单
			List<HtmlElement> list = page.getElementsByTagName("a");
			for (HtmlElement a : list) {
				if ("WB_btn_oauth formbtn_01".equals(a.getAttribute("class"))) {
					loginPage = a.click();
					break;
				}
			}

			// 取得URL
			url = loginPage.getWebResponse().getResponseHeaderValue("Location");
			if (url == null) {
				url = loginPage.getUrl().toString();
			}
			// 取得Code
			String code = "";
			if (url.contains("?code=")) {
				code = url.substring(url.indexOf("?code=") + "?code=".length());
				token = oauth.getAccessTokenByCode(code);
				return;
			}

			// 如果出现授权页面
			if (StringUtils.isEmpty(code)) {
				list = loginPage.getElementsByTagName("a");
				for (HtmlElement a : list) {
					if ("WB_btn_oauth formbtn_01".equals(a
							.getAttribute("class"))) {
						webClient.setJavaScriptTimeout(100);
						authPage = a.click();
						break;
					}
				}
				url = authPage.getWebResponse().getResponseHeaderValue(
						"Location");
				if (StringUtils.isEmpty(url)) {
					url = authPage.getUrl().toString();
				}
				if (url.contains("?code=")) {
					code = url.substring(url.indexOf("?code=")
							+ "?code=".length());
					token = oauth.getAccessTokenByCode(code);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			page.cleanUp();
			loginPage.cleanUp();
			authPage.cleanUp();
			webClient.closeAllWindows();
			webClient.setTimeout(1000);
		}
	}

	@Override
	public List<Item> filterItem(List<Item> items) {
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public List<Status> getWeibos() {
		try {
			return tm.getPublicTimeline(100, 0).getStatuses();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void login() throws Exception {
		String tokenStr = null;
		if (token == null) {
			getToken();
			tokenStr = token.getAccessToken();
		} else {
			tokenStr = token.getRefreshToken();
			if (StringUtils.isEmpty(tokenStr)) {
				getToken();
				tokenStr = token.getAccessToken();
			}
		}
		weibo.setToken(tokenStr);
	}

	@Override
	public boolean isSame(Status object1, Status object2) {
		return object1.getId().equals(object2.getId());
	}

	@Override
	public void saveItems(List<Item> items) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Item wrapItem(Status weibo) {
		// TODO Auto-generated method stub
		System.out.println(weibo.getText());
		return null;
	}

	public static void main(String[] args) {
		new Thread(new SinaWeiboClient()).run();
	}

	@Override
	public int getInterval() {
		return 3;
	}
}

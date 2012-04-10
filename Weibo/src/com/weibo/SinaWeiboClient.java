package com.weibo;

import java.util.List;

import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.examples.oauth2.Log;
import weibo4j.http.AccessToken;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SinaWeiboClient implements WeiboClient {

	private Weibo weibo = new Weibo();

	private AccessToken getToken() throws Exception {
		Oauth oauth = new Oauth();

		/* open token page */
		WebClient webClient = new WebClient();
		HtmlPage page = null;
		page = webClient.getPage(oauth.authorize("code"));

		/* submit login form */
		HtmlElement button = null;
		List<HtmlElement> list = page.getElementsByTagName("a");
		for (HtmlElement a : list) {
			if ("WB_btn_oauth formbtn_01".equals(a.getAttribute("class"))) {
				button = a;
				break;
			}
		}
		HtmlElement loginname = page.getElementById("userId");
		HtmlElement password = page.getElementById("passwd");
		loginname.setAttribute("value", "aries_monster@163.com");
		password.setAttribute("value", "heroiam");
		HtmlPage loginPage = button.click();

		/* get token from URL */
		String url = loginPage.getUrl().toString();

		/* close windows */
		webClient.closeAllWindows();

		/* return token */
		String code = url.substring(url.indexOf("?code=") + "?code=".length());
		AccessToken token = oauth.getAccessTokenByCode(code);
		return token;
	}

	@Override
	public void setUp() throws Exception {
		AccessToken token = getToken();
		weibo.setToken(token.getAccessToken());
		System.out.println("Expire: " + token.getExpireIn());
		System.out.println("Limit: " + token.getRateLimitLimit());
		System.out.println("Remaining: " + token.getRateLimitRemaining());
		System.out.println("Reset: " + token.getRateLimitReset());
	}

	@Override
	public void crawl() {
		Timeline tm = new Timeline();
		try {
			StatusWapper status = tm.getPublicTimeline();
			for (Status s : status.getStatuses()) {
				Log.logInfo(s.toString());
			}
			System.out.println(status.getNextCursor());
			System.out.println(status.getPreviousCursor());
			System.out.println(status.getTotalNumber());
			System.out.println(status.getHasvisible());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			SinaWeiboClient client = new SinaWeiboClient();
			client.setUp();
			client.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

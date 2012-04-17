package com.weibo;

import java.util.List;

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
		loginname.setAttribute("value", "sinaweibot@126.com");
		password.setAttribute("value", "5131421");
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
		AccessToken token = getToken();
		weibo.setToken(token.getAccessToken());
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

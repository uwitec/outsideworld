package weibo4j.examples.oauth2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class OAuth4Code {
	public static void main(String[] args) throws WeiboException, IOException {
		Oauth oauth = new Oauth();
		// BareBonesBrowserLaunch.openURL(oauth.authorize("code"));

		// test
		WebClient webClient = new WebClient();
		HtmlPage page = null;
		try {
			page = webClient.getPage(oauth.authorize("code"));
			System.out.println(page.asXml());
			
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

			try {
				HtmlPage loginPage = button.click();
				System.out.println(loginPage.getUrl().toString());
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			webClient.closeAllWindows();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// End Test

		System.out.println(oauth.authorize("code"));
		System.out.print("Hit enter when it's done.[Enter]:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String code = br.readLine();
		Log.logInfo("code: " + code);
		try {
			System.out.println(oauth.getAccessTokenByCode(code));
		} catch (WeiboException e) {
			if (401 == e.getStatusCode()) {
				Log.logInfo("Unable to get the access token.");
			} else {
				e.printStackTrace();
			}
		}
	}

}

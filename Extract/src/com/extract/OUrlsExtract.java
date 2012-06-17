package com.extract;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.model.Item;
import com.model.OUrl;
import com.util.CssUtil;
import com.util.UrlUtils;

public class OUrlsExtract implements Extract {

	@Override
	public void process(Item item) throws Exception {
		List<String> urls = CssUtil.getResults(item.getParsedHtml().getDoc(),
				"a", "href");
		urls.addAll(CssUtil.getResults(item.getParsedHtml().getDoc(), "iframe",
				"src"));
		for (String url : urls) {
			if (url == null || url.isEmpty()) {
				continue;
			} else if (url.equals("#")) {
				continue;
			} else if (url.startsWith("javascript:")) {
				continue;
			} else if (!url.startsWith("http://")) {
				try {
					url = new URL(new URL(item.getUrl()), url).toString();
				} catch (MalformedURLException e) {
					continue;
				}
			}
			if (!StringUtils.startsWith(url, "http://")
					&& !StringUtils.startsWith(url, "https://")) {
				url = UrlUtils.getHost(item.getUrl()) + url;

			} else if (!StringUtils.equals(UrlUtils.getHost(item.getUrl()),
					UrlUtils.getHost(url))) {
				continue;
			}
			OUrl o = new OUrl();
			o.setUrl(url);
			if (StringUtils.isBlank(item.getSourceId())) {
				o.setAuthor(UrlUtils.getHost(item.getUrl()));
			} else {
				o.setAuthor(item.getSourceId());
			}
			item.getOurls().add(o);
		}
	}
}

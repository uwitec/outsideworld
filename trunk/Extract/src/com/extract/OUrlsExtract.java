package com.extract;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.model.Item;
import com.model.OUrl;
import com.util.CssUtil;
import com.util.UrlUtils;

public class OUrlsExtract implements Extract {

	@Override
	public void process(Item item, ParsedHtml parsedHtml) throws Exception {
		List<String> urls = CssUtil
				.getResults(parsedHtml.getDoc(), "a", "href");
		for (String url : urls) {
			if (!StringUtils.startsWith(url, "http://")
					&& !StringUtils.startsWith(url, "https://")) {
				url = UrlUtils.getHost(item.getUrl()) + url;

			} else if (!StringUtils.equals(UrlUtils.getHost(item.getUrl()),
					UrlUtils.getHost(url))) {
				continue;
			}
			OUrl o = new OUrl();
			o.setUrl(url);
			if (StringUtils.isBlank(item.getSource())) {
				o.setAuthor(UrlUtils.getHost(item.getUrl()));
			} else {
				o.setAuthor(item.getSource());
			}
			item.getOurls().add(o);
		}
	}
}

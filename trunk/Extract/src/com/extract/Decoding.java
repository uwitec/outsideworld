package com.extract;

import org.apache.commons.lang.StringUtils;

import com.model.Item;
import com.util.Fetcher;

public class Decoding implements Extract {

	private Fetcher fetcher = new Fetcher();

	@Override
	public void process(Item item) throws Exception {
		if (StringUtils.isBlank(item.getEncoding())) {
			item.setStatus(false);
			return;
		}
		item.setPageString(new String(item.getRawData(), item.getEncoding()));

		/* 如果NUTCH获取的是不完整的页面 */
		if (!item.getPageString().endsWith("</html>")) {
			fetcher.fetch(item);
			item.setPageString(new String(item.getRawData(), item.getEncoding()));
		}
	}
}

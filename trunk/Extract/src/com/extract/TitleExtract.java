package com.extract;

import org.apache.commons.lang.StringUtils;

import com.model.Item;
import com.util.XPathUtil;

public class TitleExtract extends AExtract {

	@Override
	public void process(Item item, ParsedHtml parsedHtml) throws Exception {
		if (item.getTemplate() == null) {
			item.setStatus(false);
			return;
		}
		String title = extract("title", item, parsedHtml);
		// 如果没有抽取到，则使用默认的抽取策略
		if (StringUtils.isBlank(title)) {
			title = XPathUtil.getResult(parsedHtml.getNode(), "//title");
		}
		item.setTitle(title);
	}

}

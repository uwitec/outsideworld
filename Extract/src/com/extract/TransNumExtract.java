package com.extract;

import org.apache.commons.lang.StringUtils;

import com.model.Item;

public class TransNumExtract extends AExtract {

	@Override
	public void process(Item item, ParsedHtml parsedHtml) throws Exception {
		if (item.getTemplate() == null) {
			item.setStatus(false);
			return;
		}
		String transNum = extract("transNum", item, parsedHtml);
		int trans = 0;
		// 如果没有抽取到，则使用默认的抽取策略
		if (!StringUtils.isBlank(transNum) && StringUtils.isNumeric(transNum)) {
			trans = Integer.parseInt(transNum);
		}
		item.setReplyNum(trans);

	}

}

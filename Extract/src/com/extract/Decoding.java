package com.extract;

import org.apache.commons.lang.StringUtils;

import com.model.Item;

public class Decoding implements Extract {

	@Override
	public void process(Item item) throws Exception {
		if (StringUtils.isBlank(item.getEncoding())) {
			item.setStatus(false);
			return;
		}
		item.setPageString(new String(item.getRawData(), item.getEncoding()));
	}
}

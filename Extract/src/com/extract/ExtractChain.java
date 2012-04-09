package com.extract;

import java.util.List;

import com.model.Item;

public class ExtractChain implements Extract {

	private List<Extract> extracts;

	@Override
	public void process(Item item, ParsedHtml parsedHtml) throws Exception {
		for (Extract extract : extracts) {
			if (item.getPageString() != null && parsedHtml == null) {
				parsedHtml = new ParsedHtml(item.getPageString());
			}
			extract.process(item, parsedHtml);
			if (!item.isStatus()) {
				return;
			}
		}
	}

	public void setExtracts(List<Extract> extracts) {
		this.extracts = extracts;
	}
}

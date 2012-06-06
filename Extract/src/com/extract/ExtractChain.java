package com.extract;

import java.util.List;

import com.model.Item;
import com.model.ParsedHtml;

public class ExtractChain implements Extract {

	private List<Extract> extracts;

	@Override
	public void process(Item item) throws Exception {
		for (Extract extract : extracts) {
			if (item.getPageString() != null && item.getParsedHtml() == null) {
				item.setParsedHtml(new ParsedHtml(item.getPageString()));
			}
			extract.process(item);
			if (!item.isStatus()
					&& !"MetaSearch".equalsIgnoreCase(item.getType())) {
				return;
			}
		}
	}

	public void setExtracts(List<Extract> extracts) {
		this.extracts = extracts;
	}
}

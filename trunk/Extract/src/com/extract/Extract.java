package com.extract;

import com.model.Item;

public interface Extract {

	public void process(Item item, ParsedHtml parsedHtml) throws Exception;
}

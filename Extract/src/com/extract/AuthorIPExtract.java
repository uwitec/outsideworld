package com.extract;

import com.model.Item;

public class AuthorIPExtract extends AExtract {

	@Override
	public void process(Item item) throws Exception {
		if (item.getTemplate() == null) {
			item.setStatus(false);
			return;
		}
		String authorIp = extract("authorIP", item);
		item.setAuthorIP(authorIp);
	}
}

package com.extract;

import com.model.Item;

public class AuthorExtract extends AExtract {

	@Override
	public void process(Item item) throws Exception {
		if (item.getTemplate() == null) {
			item.setStatus(false);
			return;
		}
		String author = extract("author", item);
		item.setAuthor(author);
	}
}

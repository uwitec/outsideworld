package com.dao;

import com.model.crawl.ClassItem;

public interface  ClassDao {
	public void insert(ClassItem item) throws Exception;
	public void delete(int topicId) throws Exception;
}

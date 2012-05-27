package com.dao.mongo;

import com.dao.ClassDao;
import com.model.Item;
import com.model.crawl.ClassItem;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.MongoUtil;

public class ClassItemImpl implements ClassDao {

	private MongoUtil mongoDB;

	public MongoUtil getMongoDB() {
		return mongoDB;
	}

	public void setMongoDB(MongoUtil mongoDB) {
		this.mongoDB = mongoDB;
	}
	
	private DBObject trans( ClassItem item) throws Exception {
		DBObject o = new BasicDBObject();
		o.put("itemIds", item.getItemIds());
		o.put("label", item.getLable());
		o.put("topicId", item.getTopicId());
		return o;
	}
	@Override
	public void insert(ClassItem item) throws Exception {
		mongoDB.insert(trans(item), "class");

	}

}

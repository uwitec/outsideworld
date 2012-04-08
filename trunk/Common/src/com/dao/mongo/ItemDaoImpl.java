package com.dao.mongo;

import com.dao.ItemDao;
import com.model.Item;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.MongoUtil;
import com.util.SpringFactory;

public class ItemDaoImpl implements ItemDao {

	private MongoUtil mongoDB = SpringFactory.getBean("mongoDB");
	
	@Override
	public void insert(Item item) throws Exception {
		mongoDB.insert(trans(item), "story");
		
	}
	
	private DBObject trans(Item item) throws Exception {
		DBObject o = new BasicDBObject();
		o.put("title", item.getTitle());
		o.put("content", item.getContent());
		o.put("crawTime", item.getCrawlTime());
		o.put("pubTime", item.getPubTime());
		o.put("replyNum", item.getReplyNum());
		o.put("transNum", item.getTransNum());
		o.put("source", item.getSource());
		o.put("type", item.getType());
		o.put("url", item.getUrl());
		return o;
	}
	
	

}

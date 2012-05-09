package com.dao.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dao.ItemDao;
import com.model.Item;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
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
	
	private Item trans(DBObject o) throws Exception{
		Item item = new Item();
		item.setContent((String)o.get("content"));
		item.setCrawlTime((Date)o.get("crawlTime"));
		item.setTitle((String)o.get("title"));
		item.setPubTime((Date)o.get("pubTime"));
		item.setReplyNum((Integer)o.get("replyNum"));
		item.setTransNum((Integer)o.get("transNum"));
		item.setSource((String)o.get("source"));
		item.setType((String)o.get("type"));
		item.setUrl((String)o.get("url"));
		return item;
	}

	@Override
	public List<Item> poll(int num,int skipNum) throws Exception {
		List<BasicDBObject> objects = mongoDB.pollByPage("story", num,skipNum);
		List<Item> results = new ArrayList<Item>();
		for(BasicDBObject o:objects){
			results.add(trans(o));
		}
		return results;
	}

	@Override
	public DBCursor find(DBObject sample) throws Exception {
		return mongoDB.find("story", sample);
	}

	@Override
	public void update(DBObject object) throws Exception {
		mongoDB.update(object, "story");
	}
}

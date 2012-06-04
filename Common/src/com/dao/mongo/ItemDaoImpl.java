package com.dao.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import com.dao.ItemDao;
import com.model.Item;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.util.MongoUtil;

public class ItemDaoImpl implements ItemDao {

	private MongoUtil mongoDB;

	public MongoUtil getMongoDB() {
		return mongoDB;
	}

	public void setMongoDB(MongoUtil mongoDB) {
		this.mongoDB = mongoDB;
	}

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
		o.put("source", item.getSource());
		o.put("type", item.getType());
		o.put("url", item.getUrl());
		return o;
	}

	private Item trans(DBObject o) throws Exception {
		Item item = new Item();
		item.setId(((ObjectId)o.get("_id")).toString());
		item.setContent((String) o.get("content"));
		item.setCrawlTime((Date) o.get("crawlTime"));
		item.setTitle((String) o.get("title"));
		item.setPubTime((Date) o.get("pubTime"));
		item.setReplyNum((Integer) o.get("replyNum"));
		item.setSource((String) o.get("source"));
		item.setType((String) o.get("type"));
		item.setUrl(((String)o.get("url")).toString());
	    item.setNum((Integer) o.get("num"));
		return item;
	}

	@Override
	public List<Item> poll(int num) throws Exception {
		List<BasicDBObject> objects = mongoDB.pollByPage("story", num);
		List<Item> results = new ArrayList<Item>();
		for (BasicDBObject o : objects) {
			results.add(trans(o));
		}
		return results;
	}

	@Override
	public List<Item> find(DBObject sample) throws Exception {
		DBCursor cursor = mongoDB.find("story", sample);
		List<Item> items = new ArrayList<Item>();
		while (cursor != null && cursor.hasNext()) {
			DBObject o = cursor.next();
			items.add(trans(o));
		}
		return items;
	}

	@Override
	public void update(DBObject object) throws Exception {
		mongoDB.update(object, "story");
	}

	@Override
	public void publish(List<Item> items) throws Exception {
		List<DBObject> result = new ArrayList<DBObject>();
		for (Item item : items) {
			String idstr = item.getTopicIds();
			if(!StringUtils.isBlank(idstr)){
				String[] ids = idstr.split("_");
				for(String id:ids){
					DBObject o = new BasicDBObject();
					o.put("topicId", id);
					o.put("itemId", item.getId());
					result.add(o);
				}
			}
		}
		int len = items.size();
        ObjectId[] arr = new ObjectId[len];
        for(int i=0; i<len; i++){
            arr[i] = new ObjectId(items.get(i).getId());
        }
        DBObject in = new BasicDBObject("$in", arr);
        DBObject query = new BasicDBObject("_id", in);
        DBObject updateSetValue = new BasicDBObject("num", -1);
        DBObject o = new BasicDBObject("$set",updateSetValue);
		mongoDB.insert(result, "topicItem");
		mongoDB.update(query,o, "story");
	}

	@Override
	public List<Item> findPublished(DBObject sample) throws Exception {
		DBCursor cursor = mongoDB.find("published", sample);
		List<Item> items = new ArrayList<Item>();
		while (cursor != null && cursor.hasNext()) {
			DBObject o = cursor.next();
			items.add(trans(o));
		}
		return items;
	}
	
	@Override
	public DBCursor getCursor(DBObject sample) throws Exception {
		return mongoDB.find("story", sample);
	}

	@Override
	public void remove(List<Item> dels) throws Exception {
		List<String> ids = new ArrayList<String>();
		for(Item item:dels){
			ids.add(item.getId());			
		}
		mongoDB.delete(ids, "story");
	}
}

package com.dao;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import com.model.Item;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.MongoUtil;

public class ItemDaoImpl implements ItemDao {
	private MongoUtil mongoDB;

	private DBObject trans(Item item) throws Exception {
		DBObject o = new BasicDBObject();
		o.put("url", item.getClass());
		o.put("crawlTime", item.getDate());
		for (String key : item.getKeys()) {
			o.put(key, item.getField(key));
		}
		return o;
	}

	private Item trans(DBObject o) throws Exception {
		Item item = new Item();
		item.setId(((ObjectId) o.get("_id")).toString());
		item.setUrl(((String) o.get("url")).toString());
		item.setDate((Date) o.get("crawlTime"));
		Map<String, Object> map = o.toMap();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			if (!StringUtils.equals("_id", key)
					&& !StringUtils.equals("url", key)
					&& !StringUtils.equals("crawlTime", key)) {
				item.addField(key, (String) map.get(key));
			}
		}
		return item;
	}

	@Override
	public void insert(Item item) throws Exception {
		mongoDB.insert(trans(item), TABLE_NAME);
	}

	public MongoUtil getMongoDB() {
		return mongoDB;
	}

	public void setMongoDB(MongoUtil mongoDB) {
		this.mongoDB = mongoDB;
	}

}

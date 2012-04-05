package com.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

public class MongoUtil {
	private static Mongo mgo;
	private static DB db;
	static{
		try {
			mgo = new Mongo("",2585);
			db = mgo.getDB("");
		} catch (Exception e) {
		} 
	}
	
	public static WriteResult insert(List<DBObject> li, String tableName) {
		DBCollection coll = null;
		if(!StringUtils.isBlank(tableName)){
			coll = db.getCollection(tableName);
			coll.insert(li);
		}
		return null;
	}

	public static WriteResult insert(DBObject o, String tableName) {
		DBCollection coll = null;
		if(!StringUtils.isBlank(tableName)){
			coll = db.getCollection(tableName);
			coll.insert(o);
			System.out.println("insert:"+o);
		}
		return null;
	}
	
	public static WriteResult update(BasicDBObject o,String tableName) {
		DBCollection coll = null;
		if(!StringUtils.isBlank(tableName)){
			coll = db.getCollection(tableName);
			DBObject updateCondition=new BasicDBObject(); 
			updateCondition.put("url", o.get("url"));
			return coll.update(updateCondition, o);
		}
		return null;
	}
	
	public static WriteResult delete(BasicDBObject o,String tableName){
		DBCollection coll = null;
		if(!StringUtils.isBlank(tableName)){
			coll = db.getCollection(tableName);
			return coll.remove(o);
		}
		return null;
	}
	
}

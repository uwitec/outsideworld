package com.util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.expression.spel.ast.Operator;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

public class MongoUtil {
	private static Mongo mgo;
	private static DB db;
	private static DBCursor cursor;

	private MongoUtil(String host, int port, String databse) {
		try {
			mgo = new Mongo(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		db = mgo.getDB(databse);
	}

	public WriteResult insert(List<DBObject> li, String tableName) {
		DBCollection coll = null;
		if (!StringUtils.isBlank(tableName)) {
			coll = db.getCollection(tableName);
			for(DBObject o:li){
				o.put("num", 0);
			}
			coll.insert(li);
		}
		return null;
	}

	public WriteResult insert(DBObject o, String tableName) {
		DBCollection coll = null;
		if (!StringUtils.isBlank(tableName)) {
			coll = db.getCollection(tableName);
			o.put("num", 0);
			coll.insert(o);
		}
		return null;
	}

	public WriteResult update(DBObject o, String tableName) {
		DBCollection coll = null;
		if (!StringUtils.isBlank(tableName)) {
			coll = db.getCollection(tableName);
			DBObject updateCondition = new BasicDBObject();
			updateCondition.put("url", o.get("url").toString());
			DBObject updateSetValue = new BasicDBObject("$set", o);
			coll.update(updateCondition, updateSetValue);
		}
		return null;
	}
	
	public WriteResult update(DBObject query,DBObject o, String tableName) {
		DBCollection coll = null;
		if (!StringUtils.isBlank(tableName)) {
			coll = db.getCollection(tableName);
			coll.update(query, o,true,true);
		}
		return null;
	}

	public WriteResult delete(BasicDBObject o, String tableName) {
		DBCollection coll = null;
		if (!StringUtils.isBlank(tableName)) {
			coll = db.getCollection(tableName);
			return coll.remove(o);
		}
		return null;
	}
	
	public WriteResult delete(List<String> ids, String tableName) {
		DBCollection coll = null;
		int len = ids.size();
        ObjectId[] arr = new ObjectId[len];
        for(int i=0; i<len; i++){
            arr[i] = new ObjectId(ids.get(i));
        }
        DBObject in = new BasicDBObject("$in", arr);
        DBObject query = new BasicDBObject("_id", in);
		if (!StringUtils.isBlank(tableName)) {
			coll = db.getCollection(tableName);
			return coll.remove(query);
		}
		return null;
	}
	

	public DBCursor find(String tableName, DBObject sample) {
		DBCollection coll = null;
		if (!StringUtils.isBlank(tableName)) {
			coll = db.getCollection(tableName);
			return coll.find(sample);
		}
		return null;
	}
	
	public DBCursor find(String tableName, List<String> ids){
	    int len = ids.size();
        ObjectId[] arr = new ObjectId[len];
        for(int i=0; i<len; i++){
            arr[i] = new ObjectId(ids.get(i));
        }
        DBObject in = new BasicDBObject("$in", arr);
        DBObject query = new BasicDBObject("_id", in);
        return find(tableName,query);
	}

	/**
	 * 这个是常用方法，每次返回一定量的数据，类似于关系数据库中的分页
	 * 
	 * @param tableName
	 *            表名称
	 * @param num
	 *            返回数目
	 * @return
	 * @throws Exception
	 */
	public List<BasicDBObject> pollByPage(String tableName, int num)
			throws Exception {
		List<BasicDBObject> result = new ArrayList<BasicDBObject>();
		DBCollection coll = null;
		if (!StringUtils.isEmpty(tableName)) {
				coll = db.getCollection(tableName);
				DBObject con = new BasicDBObject();
				con.put("$ne",-1);
				DBObject query = new BasicDBObject();
				query.put("num", con);
				DBCursor cursor = coll.find(query).limit(num);
			while (cursor.hasNext()) {
				BasicDBObject o = (BasicDBObject) cursor.next();
				result.add(o);
			}
		}
		return result;
	}
}

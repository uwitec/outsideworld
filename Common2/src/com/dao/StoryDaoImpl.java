package com.dao;

import java.util.ArrayList;
import java.util.List;
import com.model.Story;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.MongoUtil;


public class StoryDaoImpl implements StoryDao {
    private MongoUtil mongoDB;

    @Override
    public void insert(Story story) throws Exception {
        mongoDB.insert(trans(story), "story");
    }

    @Override
    public void update(DBObject query,DBObject value) throws Exception {
        mongoDB.update(query,value, "story");
    }
    
    @Override
    public List<Story> pollReadyToDownLoad(int num) throws Exception {
        DBObject query = new BasicDBObject();
        query.put("isDownLoad", false);
        List<BasicDBObject> objects = mongoDB.pollByPage("story",num,query);
        List<Story> results = new ArrayList<Story>();
        for (BasicDBObject o : objects) {
            results.add(trans(o));
        }
        return results;
    }
    
    private BasicDBObject trans(Story story){
        BasicDBObject result = new BasicDBObject();
        result.put("description", story.getDescription());
        result.put("downloadUrl", story.getDownloadUrl());
        result.put("category", story.getCategory());
        result.put("isDownLoad", story.isDownLoad());
        result.put("result", false);
        return result;
    }
    
    private Story trans(BasicDBObject o){
        Story result = new Story();
        result.setCategory(o.getString("category"));
        result.setDownLoad(o.getBoolean("isDownLoad", false));
        result.setDescription(o.getString("description"));
        result.setDownloadUrl(o.getString("downloadUrl"));
        result.setId(o.getString("_id"));
        return result;
    }

    public MongoUtil getMongoDB() {
        return mongoDB;
    }

    public void setMongoDB(MongoUtil mongoDB) {
        this.mongoDB = mongoDB;
    }

	@Override
	public List<Story> pollHasBeenDownLoad(int num) throws Exception {
		DBObject query = new BasicDBObject();
        query.put("isDownLoad", true);
        query.put("isIndexed", false);
        List<BasicDBObject> objects = mongoDB.pollByPage("story",num,query);
        List<Story> results = new ArrayList<Story>();
        for (BasicDBObject o : objects) {
            results.add(trans(o));
        }
        return results;
	}
}

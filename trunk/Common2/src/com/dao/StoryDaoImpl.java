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
    public void update(DBObject object) throws Exception {
        mongoDB.update(object, "story");
    }
    
    @Override
    public List<Story> poll(int num) throws Exception {
        DBObject query = new BasicDBObject();
        query.put("isDownLoad", true);
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
        return result;
    }
    
    private Story trans(BasicDBObject o){
        Story result = new Story();
        result.setCategory(o.getString("category"));
        result.setDownLoad(o.getBoolean("isDownLoad", false));
        result.setDescription(o.getString("description"));
        result.setDownloadUrl(o.getString("downloadUrl"));
        return result;
    }

    public MongoUtil getMongoDB() {
        return mongoDB;
    }

    public void setMongoDB(MongoUtil mongoDB) {
        this.mongoDB = mongoDB;
    }
}

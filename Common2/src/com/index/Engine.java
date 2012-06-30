package com.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import com.model.FieldConstant;
import com.model.TableConstant;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.MongoUtil;

public class Engine {
	private MongoUtil mongoDB;
	private Index index;
    public void excute() throws Exception{
    	while(true){
    		List<BasicDBObject> stories = pollHasBeenDownLoad(100);
    		if(stories==null||stories.size()<=0){
    			Thread.sleep(1000*60*60);
    		}
    		Map<String,List<DBObject>> map =  new HashMap<String,List<DBObject>>();
    		 for (DBObject story : stories) {
    		     String key = (String)story.get(FieldConstant.CHANNEL)+File.separator+(String)story.get(FieldConstant.FORMAT);
                 if(map.containsKey(key)){
                	 map.get(key).add(story);
                 }
                 else{
                	 List<DBObject> value = new ArrayList<DBObject>();
                	 value.add(story);
                	 map.put(key, value);
                 }
                 DBObject query = new BasicDBObject();
                 query.put(FieldConstant.ID, new ObjectId(story.get(FieldConstant.ID).toString()));
                 DBObject value = new BasicDBObject();
                 value.put("$set", new BasicDBObject().append(FieldConstant.ISINDEXED, true));
                 mongoDB.update(query,value,TableConstant.TABLESTORY);
             }
    		 for(Map.Entry<String, List<DBObject>> entry:map.entrySet()){
    			 String dirName = "index"+File.separator+entry.getKey();
    			 File dir = new File(dirName);
    			 if(!dir.exists()){
    				 dir.mkdirs();
    			 }
    			 index.open(dirName);
    			 index.index(entry.getValue());
    			 index.commit();
    			 index.close();
    		 }
    		 
    	}
    }
    
    private List<BasicDBObject> pollHasBeenDownLoad(int num) throws Exception {
        DBObject query = new BasicDBObject();
        query.put("isDownLoad", true);
        query.put("isIndexed", false);
        List<BasicDBObject> objects = mongoDB.pollByPage(TableConstant.TABLESTORY,num,query);
        return objects;
    }
	
	
    public MongoUtil getMongoDB() {
        return mongoDB;
    }

    
    public void setMongoDB(MongoUtil mongoDB) {
        this.mongoDB = mongoDB;
    }

    public Index getIndex() {
		return index;
	}
	public void setIndex(Index index) {
		this.index = index;
	}
	
}

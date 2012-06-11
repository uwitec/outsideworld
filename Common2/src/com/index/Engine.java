package com.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.dao.StoryDao;
import com.model.Story;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Engine {
	private StoryDao storyDao;
	private Index index;
    public void excute() throws Exception{
    	while(true){
    		List<Story> stories = storyDao.pollHasBeenDownLoad(100);
    		if(stories==null||stories.size()<=0){
    			Thread.sleep(1000*60*60);
    		}
    		Map<String,List<Story>> map =  new HashMap<String,List<Story>>();
    		 for (Story story : stories) {
                 if(map.containsKey(story.getCategory())){
                	 map.get(story.getCategory()).add(story);
                 }
                 else{
                	 List<Story> value = new ArrayList<Story>();
                	 value.add(story);
                	 map.put(story.getCategory(), value);
                 }
                 DBObject query = new BasicDBObject();
                 query.put("_id", new ObjectId(story.getId()));
                 DBObject value = new BasicDBObject();
                 value.put("$set", new BasicDBObject().append("isIndexed", true));
                 storyDao.update(query, value);
             }
    		 for(Map.Entry<String, List<Story>> entry:map.entrySet()){
    			 index.open("index"+File.separator+entry.getKey());
    			 index.index(entry.getValue());
    			 index.commit();
    			 index.close();
    		 }
    		 
    	}
    }
	public StoryDao getStoryDao() {
		return storyDao;
	}
	public void setStoryDao(StoryDao storyDao) {
		this.storyDao = storyDao;
	}
	public Index getIndex() {
		return index;
	}
	public void setIndex(Index index) {
		this.index = index;
	}
}

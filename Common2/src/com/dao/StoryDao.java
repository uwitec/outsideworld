package com.dao;

import java.util.List;
import com.model.Story;
import com.mongodb.DBObject;


public interface StoryDao {
    public void insert(Story story) throws Exception;
    public void update(DBObject query,DBObject value) throws Exception;
    public List<Story> poll(int num) throws Exception;
}

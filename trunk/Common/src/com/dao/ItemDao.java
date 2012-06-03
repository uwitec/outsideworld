package com.dao;

import java.util.List;

import com.model.Item;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public interface ItemDao {
	public void insert(Item item) throws Exception;
	
	public void update(DBObject object) throws Exception;

	public List<Item> find(DBObject sample) throws Exception;
	
	public List<Item> poll(int num) throws Exception;
	
	public void publish(List<Item> items) throws Exception;
	
	public List<Item> findPublished(DBObject sample) throws Exception;
	
	public DBCursor getCursor(DBObject sample) throws Exception;
	
	public void remove(List<Item> dels) throws Exception;
}

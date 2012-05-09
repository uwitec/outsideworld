package com.dao;

import java.util.List;

import com.model.Item;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public interface ItemDao {
	public void insert(Item item) throws Exception;
	
	public void update(DBObject object) throws Exception;

	public DBCursor find(DBObject sample) throws Exception;
	
	public List<Item> poll(int num,int skipNum) throws Exception;
}

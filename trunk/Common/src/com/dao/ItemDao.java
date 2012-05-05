package com.dao;

import java.util.List;

import com.model.Item;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public interface ItemDao {
	public void insert(Item item) throws Exception;

	/**
	 * 从mongo中获得指定数目的item，并且将他们删除
	 * 
	 * @throws Exception
	 */
	public List<Item> poll(int num) throws Exception;
	
	public void update(DBObject object) throws Exception;

	public DBCursor find(DBObject sample) throws Exception;
}

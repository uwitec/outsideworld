package com.dao;

import com.model.Item;

public interface ItemDao {
	public static final String TABLE_NAME = "item";  
    public void insert(Item item) throws Exception;
}

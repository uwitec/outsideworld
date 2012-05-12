package com.nutch.manager;

import com.ItemIndexer;
import com.util.SpringFactory;

public class ItemIndexClient {
	public static void main(String[] args) throws Exception{
    	ItemIndexer indexer = (ItemIndexer)SpringFactory.getBean("itemIndexer");
    	indexer.index();
    }
}

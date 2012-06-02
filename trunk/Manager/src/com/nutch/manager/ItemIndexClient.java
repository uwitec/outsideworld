package com.nutch.manager;

import java.util.List;

import com.ItemIndexer;
import com.dao.CommonDAO;
import com.model.policy.Param;
import com.util.SpringFactory;

public class ItemIndexClient {
	public static void main(String[] args){
		CommonDAO commonDAO = SpringFactory.getBean("commonDAO");
    	ItemIndexer indexer = (ItemIndexer)SpringFactory.getBean("itemIndexer");
    	List<Param> indexParams = commonDAO
				.query("from Param p where p.type='index'");
    	try{
    	indexer.index(indexParams.get(0).getValue1());
    	}catch(Exception e){
    		try {
				indexer.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
    	
    }
}

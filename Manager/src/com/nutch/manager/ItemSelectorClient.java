package com.nutch.manager;

import java.util.List;

import com.dao.CommonDAO;
import com.main.ItemSelector;
import com.model.policy.Param;
import com.util.SpringFactory;

public class ItemSelectorClient {
	public static void main(String[] args) throws Exception {
		ItemSelector selector = (ItemSelector) SpringFactory
				.getBean("itemSelector");
		CommonDAO commonDao = (CommonDAO) SpringFactory.getBean("commonDAO");
		List<Param> params = commonDao
				.query("from Param p where p.type='index_dir'");
		selector.select(params.get(0).getValue1());
		System.out.println("完成");
		}
}

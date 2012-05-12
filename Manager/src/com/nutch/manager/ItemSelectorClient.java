package com.nutch.manager;

import com.ItemSelector;
import com.util.SpringFactory;

public class ItemSelectorClient {
	public static void main(String[] args) throws Exception {
		ItemSelector selector = (ItemSelector) SpringFactory
				.getBean("itemSelector");
		selector.select();
		System.out.println("完成");
		}
}

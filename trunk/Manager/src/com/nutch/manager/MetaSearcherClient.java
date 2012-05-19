package com.nutch.manager;

import com.search.MetaSearcher;

public class MetaSearcherClient {

	public static void main(String[] args) {
		new Thread(new MetaSearcher()).start();
	}
}

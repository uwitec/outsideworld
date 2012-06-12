package com.engine;

import com.spider.Spider;

public class SpiderEngine {

	private int spiderNum = 3;

	private SpiderEngine() {

		/* Start spiders */
		Thread[] spiders = new Thread[spiderNum];
		for (int i = 0; i < spiders.length; i++) {
			spiders[i] = new Spider();
			spiders[i].start();
		}
	}

	public static void main(String[] args) {
		new SpiderEngine();
	}

}

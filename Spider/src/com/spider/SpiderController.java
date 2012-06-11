package com.spider;

public class SpiderController {

	private int spiderNum = 3;

	private SpiderController() {

		/* Start spiders */
		Thread[] spiders = new Thread[spiderNum];
		for (int i = 0; i < spiders.length; i++) {
			spiders[i] = new Spider();
			spiders[i].start();
		}
	}

	public static void main(String[] args) {
		new SpiderController();
	}

}

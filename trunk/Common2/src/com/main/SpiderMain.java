package com.main;

import com.spider.insert.SiteInsert;
import com.thread.ThreadPool;
import com.util.SpringFactory;

public class SpiderMain {  
	public static void main(String[] args) throws Exception{
		new Thread(new SiteInsert()).start();
		ThreadPool fetchPool = SpringFactory.getBean("fetchPool");
		fetchPool.run((Runnable)SpringFactory.getBean("fetchControl"));
		ThreadPool extractPool = SpringFactory.getBean("extractPool");	
		extractPool.run((Runnable)SpringFactory.getBean("extractControl"));
	}
	
}

package com.nutch.manager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.cache.Cache;
import com.dao.CommonDAO;
import com.model.policy.Param;
import com.model.policy.Topic;
import com.repeate.Control;
import com.util.CacheStore;
import com.util.SpringFactory;

public class RepeateClient {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		while(true){
        Control control = SpringFactory.getBean("control");
        CacheStore cache = SpringFactory.getBean("cache");
        Cache corpusCache = SpringFactory.getBean("corpusCache");
        List<Topic> topics = cache.get("topic");
        if (topics == null || topics.size() <= 0) {
			return;
		}
        CommonDAO commonDao = (CommonDAO) SpringFactory.getBean("commonDAO");
		List<Param> params = commonDao
				.query("from Param p where p.type='corpus_file'");
        loadCache(params.get(0).getValue1(),corpusCache);
        for(Topic topic:topics){
        	control.repeate(topic, corpusCache);
        }
        Thread.sleep(1000*60*60*24);
		}
	}
	
	/**
	 * 加载预料库到cache中
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	private static void loadCache(String fileName,Cache corpusCache) throws Exception {
		InputStream input = new FileInputStream(fileName);
		BufferedReader bufferInput = new BufferedReader(new InputStreamReader(
				input, "GBK"));
		String tmp = null;
		String[] datas = null;
		
		while ((tmp = bufferInput.readLine()) != null) {
			datas = tmp.split("\\s");
			corpusCache.put(datas[0], Integer.parseInt(datas[1]));
		}
	}
}

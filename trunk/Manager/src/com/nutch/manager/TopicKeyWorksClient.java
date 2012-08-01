package com.nutch.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import com.algorithm.SegSentence;
import com.dao.CommonDAO;
import com.dao.ItemDao;
import com.model.Item;
import com.model.WordItem;
import com.model.policy.Topic;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.CacheStore;
import com.util.MongoUtil;
import com.util.SpringFactory;

public class TopicKeyWorksClient {
	public static void main(String[] args) throws Exception {
		while (true) {
			CacheStore cache = SpringFactory.getBean("cache");
			List<Topic> topics = cache.get("topic");
			if (topics == null || topics.size() <= 0) {
				return;
			}
			CommonDAO commonDao = (CommonDAO) SpringFactory
					.getBean("commonDAO");
			ItemDao itemDao = SpringFactory.getBean("itemDao");
			MongoUtil mongoDB = SpringFactory.getBean("mongoDB");
			SegSentence sentence = SpringFactory.getBean("segSentence");
			for (Topic topic : topics) {
				int topicId = topic.getId();
				List<Item> items = itemDao.findByTopicId(String
						.valueOf(topicId));
				if (items != null && items.size() > 0) {
					Map<String, List<String>> wordsMap = new HashMap<String, List<String>>();
					for (Item item : items) {
						List<String> words = new ArrayList<String>();
						if (!StringUtils.isBlank(item.getTitle())) {
							words.addAll(sentence.seg(item.getTitle()));
						}
						if (!StringUtils.isBlank(item.getContent())) {
							words.addAll(sentence.seg(item.getContent()));
						}
						if (words.size() > 0) {
							wordsMap.put(item.getId(), words);
						}
					}
					Map<String, Integer> keys = new HashMap<String, Integer>();
					for (Map.Entry<String, List<String>> entry : wordsMap
							.entrySet()) {
						for (String key : entry.getValue()) {
							if (keys.containsKey(key)) {
								keys.put(key, keys.get(key) + 1);
							} else {
								keys.put(key, 1);
							}
						}
					}
					if (keys.size() > 0) {
						List<WordItem> wordItems = new ArrayList<WordItem>();
						for (Map.Entry<String, Integer> entry : keys.entrySet()) {
							if (StringUtils.indexOf(topic.getInclude(),
									entry.getKey()) < 0) {
								WordItem wordItem = new WordItem();
								wordItem.setWord(entry.getKey());
								wordItem.setRate(entry.getValue());
								wordItems.add(wordItem);
							}
						}
						Collections.sort(wordItems); 
						String result = "";
						for (int i = 0; i < wordItems.size() && i < 10; i++) {
							result+=wordItems.get(i).getWord()+",";
						}
						topic.setKeywords(result);
						commonDao.update(topic);
						for (Map.Entry<String, List<String>> entry : wordsMap
								.entrySet()) {
							String itemResult = "";
							for(String k:entry.getValue()){
							    if(StringUtils.indexOf(result, k)>=0){
							    	itemResult+=k;
							    }
							    
							}
							DBObject query = new BasicDBObject("_id", new ObjectId(entry.getKey()));
							DBObject updateSetValue = new BasicDBObject("keywords", itemResult);
							DBObject o = new BasicDBObject("$set", updateSetValue);
							mongoDB.update(query, o, "item");
						}
					}
				}
			}
			Thread.sleep(1000 * 60 * 60 * 24);
		}
	}
}

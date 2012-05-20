package com;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.algorithm.Agents;
import com.algorithm.ConcentricString;
import com.algorithm.KWordsSelector;
import com.cache.Cache;
import com.dao.CommonDAO;
import com.dao.ItemDao;
import com.model.Item;
import com.model.TopicItem;
import com.model.policy.Topic;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.CacheStore;
import com.util.SpringFactory;

public class Main {
	private Cache corpusCache;
	private Cache wordsCache;
	private Cache dfCache;
	private ItemDao itemDAO;
	private ConcentricString concentricString;
	private KWordsSelector kWordsSelector;
	private Agents agents;
	private CacheStore cache;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		// 读取语料库的配置文件
		main.loadCache("topic_ngram_result.txt");
		// 表示读取语料库已经完成
		System.out.println("语料库已经加载完成");
		// 获得所有的topic
		List<Topic> topics = main.getCache().get("topic");
		ItemDao itemDao = main.getItemDAO();
		for (Topic topic : topics) {
			List<String> topicWords = Arrays.asList(topic.getInclude().split("；"));
			String id = String.valueOf(topic.getId());
			DBObject sample = new BasicDBObject();
			List<Item> items = itemDao.find(sample);
			List<TopicItem> topicItems = new ArrayList<TopicItem>();
			for (Item item : items) {
				TopicItem topicItem = new TopicItem();
				topicItem.setContent(item.getContent());
				topicItem.setTitle(item.getTitle());
				Set<String> keyWords = new HashSet<String>();
				if (!StringUtils.isBlank(item.getContent())) {
					topicItem.setRawCStrings(main.getConcentricString()
							.concentric(item.getContent()));
					keyWords.addAll(topicItem.getRawCStrings());
					for(String word:topicItem.getRawCStrings()){
						main.getWordsCache().addWord(word, 1);
					}
				}
				if (!StringUtils.isBlank(item.getTitle())) {
					topicItem.setRawCStrings(main.getConcentricString()
							.concentric(item.getTitle()));
					keyWords.addAll(topicItem.getRawTStrings());
					for(String word:topicItem.getRawTStrings()){
						main.getWordsCache().addWord(word, 2);
					}
				}
				for (String word : keyWords) {
					main.getDfCache().addWord(word, 1);
				}
				topicItem.setWords(main.getkWordsSelector().select(keyWords, items.size(), topicWords));
				topicItems.add(topicItem);
			}
			main.getAgents().clustering(topicItems);
			main.getWordsCache().clear();
			main.getDfCache().clear();
			Collections.sort(topicItems);
		}
	}

	/**
	 * 加载预料库到cache中
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	private void loadCache(String fileName) throws Exception {
		InputStream input = new FileInputStream(fileName);
		BufferedReader bufferInput = new BufferedReader(new InputStreamReader(
				input, "GBK"));
		String tmp = null;
		String[] datas = null;
		Cache corpusCache = SpringFactory.getBean("corpusCache");
		while ((tmp = bufferInput.readLine()) != null) {
			datas = tmp.split("\\t");
			corpusCache.put(datas[0], Integer.parseInt(datas[1]));
		}
	}

	public Cache getCorpusCache() {
		return corpusCache;
	}

	public void setCorpusCache(Cache corpusCache) {
		this.corpusCache = corpusCache;
	}

	public Cache getWordsCache() {
		return wordsCache;
	}

	public void setWordsCache(Cache wordsCache) {
		this.wordsCache = wordsCache;
	}

	public Cache getDfCache() {
		return dfCache;
	}

	public void setDfCache(Cache dfCache) {
		this.dfCache = dfCache;
	}

	public ItemDao getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(ItemDao itemDAO) {
		this.itemDAO = itemDAO;
	}

	public ConcentricString getConcentricString() {
		return concentricString;
	}

	public void setConcentricString(ConcentricString concentricString) {
		this.concentricString = concentricString;
	}

	public KWordsSelector getkWordsSelector() {
		return kWordsSelector;
	}

	public void setkWordsSelector(KWordsSelector kWordsSelector) {
		this.kWordsSelector = kWordsSelector;
	}

	public Agents getAgents() {
		return agents;
	}

	public void setAgents(Agents agents) {
		this.agents = agents;
	}

	public CacheStore getCache() {
		return cache;
	}

	public void setCache(CacheStore cache) {
		this.cache = cache;
	}

}

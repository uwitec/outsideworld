package com.repeate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.algorithm.Agents;
import com.algorithm.KWordsSelector;
import com.algorithm.SegSentence;
import com.cache.Cache;
import com.dao.ClassDao;
import com.dao.ItemDao;
import com.model.Item;
import com.model.ReTopicItem;
import com.model.WordItem;
import com.model.crawl.ClassItem;
import com.model.policy.Topic;
import com.util.CacheStore;

public class Control {
	private static Logger LOG = Logger.getLogger(Control.class);
	private Cache corpusCache;
	private ItemDao itemDao;
	private KWordsSelector kWordsSelector;
	private Agents agents;
	private CacheStore cache;
	private SegSentence segSentence;
	private ClassDao classDao;

	/**
	 * 针对一个topic进行重新去重
	 * 
	 * @throws Exception
	 */
	public void repeate(Topic topic, Cache corpusCache) throws Exception {
		LOG.info("The current topic is " + topic.getName());
		List<String> topicWords = Arrays.asList(topic.getInclude().split("；"));
		LOG.info("The topicWords is" + topicWords.toString());
		Cache wordsCache = new Cache();
		Cache dfCache = new Cache();
		List<Item> items = itemDao.findByTopicId(String.valueOf(topic.getId()));
		LOG.info("There is " + items.size() + " items be ralated!");
		List<ReTopicItem> topicItems = new ArrayList<ReTopicItem>();
		LOG.info("Feature selected begin!");
		for (Item item : items) {
			ReTopicItem topicItem = new ReTopicItem();
			topicItem.setId(item.getId());
			topicItem.setContent(item.getContent());
			topicItem.setTitle(item.getTitle());
			Set<String> keyWords = new HashSet<String>();
			// 针对每个item的content，进行分词，将分出来的词语保存在wordsCache中
			if (!StringUtils.isBlank(item.getContent())) {
				topicItem.setRawCStrings(segSentence.seg(item.getContent()));
				keyWords.addAll(topicItem.getRawCStrings());
				for (String word : topicItem.getRawCStrings()) {
					wordsCache.addWord(word, 1);
				}
			}
			// 针对每个item的title，进行分词，将分出来的词语保存在wordsCache中，由于title在系统中的权重比较大，因此title中的词语出现一次算作两次
			if (!StringUtils.isBlank(item.getTitle())) {
				topicItem.setRawTStrings(segSentence.seg(item.getTitle()));
				keyWords.addAll(topicItem.getRawTStrings());
				for (String word : topicItem.getRawTStrings()) {
					wordsCache.addWord(word, 2);
				}
			}
			// 将每个item看作一篇文本，针对文本中的词语，保存这批item的df值
			for (String word : keyWords) {
				dfCache.addWord(word, 1);
			}
			// 针对选择出来的keyWords做选择
			topicItem.setWords(kWordsSelector.select(keyWords, items.size(),
					topicWords, dfCache, wordsCache));
			topicItems.add(topicItem);
		}
		classDao.delete(topic.getId());
		if (topicItems.size() > 0) {
			agents.clustering(topicItems);
			save(topicItems, topic.getId());
		}

	}

	private void save(List<ReTopicItem> topicItems, int topicId)
			throws Exception {
		int lable = 0;
		String topicIds = "";
		for (ReTopicItem ti : topicItems) {
			LOG.debug((ti.getLabel() + "****" + ti.getTitle() + "    "
					+ ti.getContent() + "    "));
			String keyWords = "";
			for (WordItem key : ti.getWords()) {
				keyWords += key.getWord() + " ";
			}
			LOG.debug("The key words is:");
			LOG.debug(keyWords);
			topicIds += ti.getId() + "_";
			if (ti.getLabel() > lable) {
				ClassItem cla = new ClassItem();
				cla.setItemIds(topicIds);
				cla.setLable(lable);
				cla.setTopicId(topicId);
				classDao.insert(cla);
				LOG.debug("Insert a class.The topicId is " + topicId
						+ ",the class is " + lable + ",the itemIds is "
						+ topicIds);
				lable = ti.getLabel();
				topicIds = "";
			}

		}
	}

	public Cache getCorpusCache() {
		return corpusCache;
	}

	public void setCorpusCache(Cache corpusCache) {
		this.corpusCache = corpusCache;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
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

	public SegSentence getSegSentence() {
		return segSentence;
	}

	public void setSegSentence(SegSentence segSentence) {
		this.segSentence = segSentence;
	}

	public ClassDao getClassDao() {
		return classDao;
	}

	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}

}

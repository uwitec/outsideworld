package com.repeate;

import java.util.List;
import org.apache.log4j.Logger;
import com.algorithm.Agents;
import com.algorithm.KWordsSelector;
import com.algorithm.SegSentence;
import com.cache.Cache;
import com.dao.ClassDao;
import com.dao.ItemDao;
import com.model.Item;
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
     * @throws Exception
     */
    public void repeate(Topic topic,Cache corpusCache) throws Exception{
        LOG.info("The current topic is "+topic.getName());
        Cache wordsCache = new Cache();
        Cache dfCache = new Cache();
        List<Item> items = itemDao.findByTopicId(String.valueOf(topic.getId()));
        LOG.info("There is "+items.size()+" items be ralated!");
        
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

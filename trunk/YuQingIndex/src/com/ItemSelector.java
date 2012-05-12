package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import com.dao.ItemDao;
import com.index.AbstractIndex;
import com.model.Item;
import com.model.policy.Topic;
import com.util.CacheStore;
import com.util.SpringFactory;

/**
 * 在内存中建立索引，针对所有生效的topic做查询，如果有一个topic命中，则保留item，否则删除
 * <p>
 * 类说明
 * </p>
 * <p>
 * Copyright: 版权所有 (c) 2010 - 2030
 * </p>
 * <p>
 * Company: Travelsky
 * </p>
 * 
 * @author fangxia722
 * @version 1.0
 * @since May 3, 2012
 */
public class ItemSelector {
	private ItemDao itemDao;
	private CacheStore cache;
	private AbstractIndex index;
	private IndexSearcher searcher;

	public List<Item> select(List<Item> items, List<Topic> topics)
			throws Exception {
		if(items==null||items.size()<=0){
			return new ArrayList<Item>();
		}
		// 定义一个Map，将所有的items定义在map中
		Map<String, Item> map = new HashMap<String, Item>();
		for (Item item : items) {
			map.put(item.getUrl(), item);
		}
		// 定义set结果
		Set<Item> setResult = new HashSet<Item>();
		// 打开RAMDirectory
		index.open("");
		// 建立内存索引
		index.index(items);
		index.commit();

		searcher = new IndexSearcher(index.getDir());
		// 对每个topic建立一次查询
		for (Topic topic : topics) {
			BooleanQuery query = new BooleanQuery();
			BooleanQuery titleQuery = new BooleanQuery();
			BooleanQuery contentQuery = new BooleanQuery();
			if (!StringUtils.isBlank(topic.getInclude())) {
				String[] musts = topic.getInclude().split(";");
				for (String must : musts) {
					BooleanClause.Occur b = BooleanClause.Occur.MUST;
					titleQuery.add(new TermQuery(new Term("title", must)), b);
					contentQuery.add(new TermQuery(new Term("content", must)), b);
				}
			}
			if (!StringUtils.isBlank(topic.getExclude())) {
				String[] mustNots = topic.getExclude().split(";");
				for (String mustNot : mustNots) {
					BooleanClause.Occur b = BooleanClause.Occur.MUST_NOT;
					titleQuery.add(new TermQuery(new Term("title", mustNot)), b);
					contentQuery.add(new TermQuery(new Term("content", mustNot)), b);
				}
			}
			query.add(titleQuery, BooleanClause.Occur.SHOULD);
			query.add(contentQuery, BooleanClause.Occur.SHOULD);
			TopDocs hits = searcher.search(query, items.size());
			if (hits != null && hits.totalHits > 0) {
				for (ScoreDoc scoreDoc : hits.scoreDocs) {
					Document doc = searcher.doc(scoreDoc.doc);
					String id = doc.get("id");
					Item item = map.get(id);
					item.setTopicIds(item.getTopicIds()+topic.getId());
					setResult.add(item);
					System.out.println(item.getContent());
				}
			}
		}
		index.close();
		// 将内存释放
		index.getDir().close();
		List<Item> result = new ArrayList<Item>();
		result.addAll(setResult);
		return result;
	}

	public AbstractIndex getIndex() {
		return index;
	}

	public void setIndex(AbstractIndex index) {
		this.index = index;
	}

	public void select() throws Exception {
		List<Item> items = null;
		List<Topic> topics = cache.get("topic");
		if (topics == null || topics.size() <= 0) {
			return;
		}
		int i = 0;
		do {
			items = itemDao.poll(1000, i * 1000);
			itemDao.publish(select(items, topics));
			i++;
		} while (items != null&&items.size()>0);
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public CacheStore getCache() {
		return cache;
	}

	public void setCache(CacheStore cache) {
		this.cache = cache;
	}

	public static void main(String[] args) throws Exception {
		ItemSelector selector = (ItemSelector) SpringFactory
				.getBean("itemSelector");
		selector.select();
		System.out.println("完成");
		}
}

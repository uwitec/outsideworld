package com.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

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
	private static Logger LOG = Logger.getLogger(ItemSelector.class);
	private ItemDao itemDao;
	private CacheStore cache;
	private AbstractIndex index;
	private AbstractIndex indexImpl;
	private ItemIndexer itemIndexer;
	private IndexSearcher searcher;

	public List<Item> select(List<Item> items, List<Topic> topics, String dir)
			throws Exception {
		if (items == null || items.size() <= 0) {
			return new ArrayList<Item>();
		}
		// 定义一个Map，将所有的items定义在map中
		Map<String, Item> map = new HashMap<String, Item>();
		for (Item item : items) {
			map.put(item.getId(), item);
		}
		// 定义set结果
		Set<Item> setResult = new HashSet<Item>();
		LOG.debug("Begin to index for the items ...");
		// 打开RAMDirectory
		index.open("");
		// 建立内存索引
		index.index(items);
		index.commit();
		LOG.debug("Index end!");
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
					contentQuery.add(new TermQuery(new Term("content", must)),
							b);
				}
			}
			if (!StringUtils.isBlank(topic.getExclude())) {
				String[] mustNots = topic.getExclude().split(";");
				for (String mustNot : mustNots) {
					BooleanClause.Occur b = BooleanClause.Occur.MUST_NOT;
					titleQuery
							.add(new TermQuery(new Term("title", mustNot)), b);
					contentQuery.add(
							new TermQuery(new Term("content", mustNot)), b);
				}
			}
			if(!StringUtils.isBlank(topic.getOptional())){
			    String[] mustNots = topic.getOptional().split(";");
                for (String mustNot : mustNots) {
                    BooleanClause.Occur b = BooleanClause.Occur.SHOULD;
                    titleQuery
                            .add(new TermQuery(new Term("title", mustNot)), b);
                    contentQuery.add(
                            new TermQuery(new Term("content", mustNot)), b);
                }
			}
			query.add(titleQuery, BooleanClause.Occur.SHOULD);
			query.add(contentQuery, BooleanClause.Occur.SHOULD);
			LOG.debug("The query is " + query.toString());
			TopDocs hits = searcher.search(query, items.size());
			//准备高亮显示，将高亮的文本存储到数据库中
			Formatter formatter = new SimpleHTMLFormatter("<span class=\"highlighter\">", "</span>");   
            Scorer fragmentScorer = new QueryScorer(query);   
            Highlighter highlighter = new Highlighter(formatter, fragmentScorer);   
            Fragmenter fragmenter = new SimpleFragmenter(100);   //高亮范围   
            highlighter.setTextFragmenter(fragmenter);   

			if (hits != null && hits.totalHits > 0) {
				LOG.debug("There is " + hits.totalHits + " items found!");
				for (ScoreDoc scoreDoc : hits.scoreDocs) {
					Document doc = searcher.doc(scoreDoc.doc);
					String id = doc.get("id");
					Item item = map.get(id);
					if (item != null) {
						item.setScore(scoreDoc.score);
						if (!StringUtils.isBlank(item.getTopicIds())) {
							item.setTopicIds(item.getTopicIds() + topic.getId()
									+ ",");
						} else {
							item.setTopicIds("" + topic.getId());
						}
						String fragTitle = doc.get("title")!=null?highlighter.getBestFragment(AbstractIndex.getAnalyzer(), "title", doc.get("title")):"";
						String fragContent =  doc.get("content")!=null?highlighter.getBestFragment(AbstractIndex.getAnalyzer(), "content", doc.get("content")):"";
						String frag = (fragTitle!=null?fragTitle:"")+(fragContent!=null?fragContent:"");
						item.setFragmenter(frag);
						setResult.add(item);
						LOG.debug("Item title:" + item.getTitle()
								+ " Item content:" + item.getContent());
					}
				}
				LOG.debug("The query " + query.toString() + " selected end!");
			}
		}
		index.close();
		// 将内存释放
		index.getDir().close();
		LOG.debug("Mem index has been closed!");
		List<Item> result = new ArrayList<Item>();
		result.addAll(setResult);
		items.removeAll(result);
		LOG.debug("There is " + result.size() + " items to be published,and "
				+ items.size() + " items to be deleted!");
		LOG.debug("Begin to index in disk!");
		itemIndexer.index(result, items, dir);
		LOG.debug("End index in disk!");
		return result;
	}


	public AbstractIndex getIndex() {
		return index;
	}

	public void setIndex(AbstractIndex index) {
		this.index = index;
	}

	public void select(String dir) throws Exception {
		LOG.info("Begin to select...");		
		do {
			List<Topic> topics = cache.get("topic");
			if (topics == null || topics.size() <= 0) {
				LOG.info("There is no topics,so exit!");
				return;
			}
			LOG.info("Begin to get items form mongoDB ...");
			List<Item> items  = itemDao.poll(1000);
			LOG.info("Got items end!");
			if (items == null || items.size() <= 0) {
				LOG.info("There is no items in mongodb,so thread sleep 60 second!");
				Thread.sleep(5000);
				continue;
			}
			LOG.info("Begin to select items by the topics...");
			List<Item> result = select(items, topics, dir);
			LOG.info("There is " + result.size() + " items selected!");
			LOG.info("Begin to publish items ...");
			itemDao.publish(result);
			LOG.info("Publish end!");
			LOG.info(System.getProperty("line.separator"));
			LOG.info(System.getProperty("line.separator"));
			LOG.info(System.getProperty("line.separator"));
		} while (true);
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

	public AbstractIndex getIndexImpl() {
		return indexImpl;
	}

	public void setIndexImpl(AbstractIndex indexImpl) {
		this.indexImpl = indexImpl;
	}

	public static void main(String[] args) throws Exception {
		ItemSelector selector = (ItemSelector) SpringFactory
				.getBean("itemSelector");
		selector.select("/home/fangxia722/index");
		System.out.println("完成");
	}

	public ItemIndexer getItemIndexer() {
		return itemIndexer;
	}

	public void setItemIndexer(ItemIndexer itemIndexer) {
		this.itemIndexer = itemIndexer;
	}

}

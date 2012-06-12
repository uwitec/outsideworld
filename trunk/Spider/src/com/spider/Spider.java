package com.spider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dao.CommonDAO;
import com.entity.Source;
import com.mongodb.BasicDBObject;
import com.util.MongoUtil;
import com.util.SpringFactory;

public class Spider extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(Spider.class);

	private static ConcurrentLinkedQueue<Page> urlQueue = new ConcurrentLinkedQueue<Page>();

	private static HtmlCleaner htmlCleaner = new HtmlCleaner();

	private static Map<String, BloomFilter> urlFilter = new HashMap<String, BloomFilter>();

	private static Map<String, Integer> depthMap = new HashMap<String, Integer>();

	private static Map<String, Integer> intervalMap = new HashMap<String, Integer>();

	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	private static MongoUtil mongoDB = SpringFactory.getBean("mongoDB");

	private static Lock lock = new ReentrantLock();

	private Fetcher fetcher;

	public Spider() {
		LOG.info("Init Spider: {}", getName());
		fetcher = new Fetcher();

		lock.lock();
		try {
			if (urlQueue.size() == 0) {
				initUrlQueue();
			}
		} catch (Exception e) {
			LOG.error("Init URL Queue Error", e);
		} finally {
			lock.unlock();
		}

		LOG.info("Init Spider: {} Successfully", getName());
	}

	@Override
	public void run() {
		while (true) {
			try {
				Page page = null;
				if ((page = urlQueue.poll()) == null) {
					LOG.info("Waiting for URL Queue");
					Thread.sleep(1000);
					continue;
				}

				fetcher.fetch(page);
				LOG.info("Fetch Html from {} Successfully", page.getUrl());

				if (page.getDepth() < depthMap.get(page.getUrl().getHost())) {
					updateUrlQueue(page);
				}

				afterCrawled(page);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void initUrlQueue() {
		List<Source> sources = commonDAO.getAll(Source.class);
		Page page = null;

		for (Source source : sources) {
			URL url = null;
			try {
				url = new URL(source.getUrl());
			} catch (MalformedURLException e) {
				LOG.error("Error URL: {}", source.getUrl());
				continue;
			}

			depthMap.put(url.getHost(), source.getDepth());
			intervalMap.put(url.getHost(), source.getInterval());

			page = new Page();
			page.setUrl(url);
			page.setDepth(0);
			urlQueue.add(page);
		}
	}

	private void updateUrlQueue(Page page) throws Exception {
		List<Page> pages = new ArrayList<Page>();
		if (page.getHtml() == null) {
			return;
		}

		LOG.info("Collect URLs from {}", page.getUrl());

		TagNode doc = htmlCleaner.clean(page.getHtml());
		Object[] hrefs = doc.evaluateXPath("//a/@href");

		/* Get BloomFilter */
		String host = page.getUrl().getHost();
		BloomFilter bloomFilter = urlFilter.get(host);
		if (bloomFilter == null) {
			int interval = intervalMap.get(host);
			bloomFilter = new BloomFilter(interval * 3600 * 1000);
		}
		if (bloomFilter.isExpire()) {
			int interval = intervalMap.get(host);
			bloomFilter = new BloomFilter(interval * 3600 * 1000);
			urlFilter.put(host, bloomFilter);
		}

		Page subPage = null;
		for (Object href : hrefs) {
			/* filter invalid link */
			String link = href.toString();
			if (!filterUrl(link)) {
				continue;
			}

			URL url = null;
			try {
				url = new URL(link);
			} catch (Exception e) {
				continue;
			}

			/* whether inner link */
			if (!url.getHost().equals(page.getUrl().getHost())) {
				continue;
			}

			subPage = new Page();
			subPage.setUrl(url);

			/* check duplicated and interval */
			if (bloomFilter.contains(url.getPath())) {
				continue;
			} else {
				bloomFilter.add(url.getPath());

				pages.add(subPage);
				subPage.setDepth(page.getDepth() + 1);
			}
		}
		urlQueue.addAll(pages);
		LOG.info("Collect {} URLs from {}", pages.size(), page.getUrl());
	}

	private boolean filterUrl(String url) {
		if (url == null || url.isEmpty()) {
			return false;
		} else if (url.equals("#")) {
			return false;
		} else if (url.startsWith("javascript:")) {
			return false;
		} else {
			return true;
		}
	}

	private void afterCrawled(Page page) {
		BasicDBObject result = new BasicDBObject();
		result.put("url", page.getUrl().toString());
		result.put("html", page.getHtml());
		if (page.getHtml() == null || page.getHtml().length() < 1)
			System.out.println("------------" + page.getUrl().toString());
		;
		result.put("crawlTime", new Date());
		mongoDB.insert(result, "page");
	}
}

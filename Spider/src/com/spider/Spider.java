package com.spider;

import java.util.ArrayList;
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

public class Spider extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(Spider.class);

	private static ConcurrentLinkedQueue<Page> urlQueue = new ConcurrentLinkedQueue<Page>();

	private static HtmlCleaner htmlCleaner = new HtmlCleaner();

	private static Map<String, BloomFilter> urlFilter = new HashMap<String, BloomFilter>();

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
				LOG.debug("URL Queue size: {}", urlQueue.size());
				if (urlQueue.size() == 0) {
					LOG.info("Waiting for URL Queue");
					Thread.sleep(1000);
					continue;
				}

				LOG.debug("Try to get URL from URL Queue");
				Page page = urlQueue.poll();

				LOG.info("Fetch {}", page.getUrl());
				fetcher.fetch(page);
				LOG.info("Fetch Html from {} Successfully", page.getUrl());

				updateUrlQueue(page);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void initUrlQueue() {
		// TODO
		Page p1 = new Page();
		p1.setUrl("http://www.163.com/");
		urlQueue.add(p1);

		Page p2 = new Page();
		p2.setUrl("http://www.ifeng.com/");
		urlQueue.add(p2);

		Page p3 = new Page();
		p3.setUrl("http://www.qq.com/");
		urlQueue.add(p3);
	}

	private boolean isValid(Page page) {
		String domain = "";// TODO
		BloomFilter bloomFilter = urlFilter.get(domain);
		if (bloomFilter == null) {
			bloomFilter = new BloomFilter(3600);// TODO
			return true;
		}
		if (bloomFilter.isExpire()) {
			bloomFilter = new BloomFilter(3600);// TODO
			urlFilter.put(domain, bloomFilter);
		}

		if (bloomFilter.contains(page.getUrl())) {
			return false;
		} else {
			bloomFilter.add(page.getUrl());
			return true;
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

		Page subPage = null;
		for (Object href : hrefs) {
			String url = href.toString();
			if (!filterUrl(url)) {
				continue;
			}

			subPage = new Page();
			subPage.setUrl(url);
			if (isValid(subPage)) {
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
}

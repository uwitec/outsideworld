package com.spider;

import java.net.MalformedURLException;
import java.net.URL;
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
import com.extract.Extractor;
import com.model.Page;
import com.util.SpringFactory;

public class Spider extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(Spider.class);

	private static ConcurrentLinkedQueue<Page> urlQueue = new ConcurrentLinkedQueue<Page>();

	private static HtmlCleaner htmlCleaner = new HtmlCleaner();

	private static Map<String, BloomFilter> urlFilter = new HashMap<String, BloomFilter>();

	private static Map<String, Integer> depthMap = new HashMap<String, Integer>();

	private static Map<String, Integer> intervalMap = new HashMap<String, Integer>();

	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

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

			/* Get BloomFilter */
			String host = page.getUrl().getHost();
			BloomFilter bloomFilter = urlFilter.get(host);
			if (bloomFilter == null || bloomFilter.isExpire()) {
				int interval = intervalMap.get(host);
				bloomFilter = new BloomFilter(interval * 3600 * 1000);
				urlFilter.put(host, bloomFilter);
			}
			bloomFilter.add(url.getPath());
		}
	}

	private void updateUrlQueue(Page page) throws Exception {
		if (page.getHtml() == null) {
			return;
		}

		LOG.info("Collect URLs from {}", page.getUrl());

		TagNode doc = htmlCleaner.clean(page.getHtml());
		page.setDoc(doc);
		Object[] hrefs = doc.evaluateXPath("//a/@href");

		/* Get BloomFilter */
		String host = page.getUrl().getHost();
		BloomFilter bloomFilter = urlFilter.get(host);
		if (bloomFilter == null || bloomFilter.isExpire()) {
			int interval = intervalMap.get(host);
			bloomFilter = new BloomFilter(interval * 3600 * 1000);
			urlFilter.put(host, bloomFilter);
		}

		Page subPage = null;
		int pageCount = 0;
		for (Object href : hrefs) {
			/* filter invalid link */
			String link = href.toString();
			if ((link = filterUrl(page.getUrl(), link)) == null) {
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

				subPage.setDepth(page.getDepth() + 1);
				urlQueue.offer(subPage);
				pageCount++;
			}
		}
		LOG.info("Collect {} URLs from {}", pageCount, page.getUrl());
	}

	private String filterUrl(URL parentUrl, String url) {
		if (url == null || url.isEmpty()) {
			return null;
		} else if (url.equals("#")) {
			return null;
		} else if (url.startsWith("javascript:")) {
			return null;
		} else if (!url.startsWith("http://")) {
			try {
				return new URL(parentUrl, url).toString();
			} catch (MalformedURLException e) {
				return null;
			}
		} else {
			return url;
		}
	}

	private void afterCrawled(Page page) {
		Extractor.addPage(page);
	}
}

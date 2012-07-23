package org.apache.nutch;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.crawl.Crawl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.model.policy.Source;

public class NutchCrawler {

	private static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"nutchContext.xml");

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanid) {
		return (T) appContext.getBean(beanid);
	}

	private static Context context = new Context();

	// TODO fitler
	// private static BloomFilter bloomFilter = new BloomFilter(new File(
	// context.getCrawlFilter()), 3600 * 1000);
	//
	// public static void addUrl(String url) {
	// bloomFilter.add(url);
	// }
	//
	// public static boolean contains(String url) {
	// return bloomFilter.contains(url);
	// }

	/* 启动 Nutch */
	private static void startCrawl() throws Exception {

		while (true) {
			/* inject root URLs */
			List<String> urls = new LinkedList<String>();
			for (Source site : context.getSites()) {
				urls.add(site.getUrl());
			}
			new UrlManager(context).injectRootURLs(urls);

			/* crawl parameters */
			String[] args = new String[9];
			args[0] = context.getCrawlUrls();
			args[1] = "-dir";
			args[2] = context.getCrawlDB();
			args[3] = "-threads";
			args[4] = context.getCrawlThread();
			args[5] = "-depth";
			args[6] = context.getCrawlDepth();
			args[7] = "-topN";
			args[8] = context.getCrawlTopN();

			/* clear crawlDB */
			File crawlDB = new File(context.getCrawlDB());
			FileUtils.deleteQuietly(crawlDB);

			/* start Crawl */
			try {
				ToolRunner.run(context.getNutchConfig(), new Crawl(), args);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Thread.sleep(1000 * 1800);
		}
	}

	public static void main(String[] args) throws Exception {
		startCrawl();
	}

}

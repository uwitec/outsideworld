package com.nutch.manager;

import java.util.LinkedList;
import java.util.List;

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

	/* 启动 Nutch */
	private static int startCrawl() throws Exception {

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

		/* start Crawl */
		int rc = ToolRunner.run(context.getNutchConfig(), new Crawl(), args);
		return rc;
	}

	public static void main(String[] args) throws Exception {
		startCrawl();
	}

}

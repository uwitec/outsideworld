package com.nutch.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.crawl.Crawl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static Context context = new Context(applicationContext);

	/* 启动 Crawl */
	private static int startCrawl() throws Exception {
		// clear crawl DB
		FileUtils.cleanDirectory(new File(context.getCrawlDB()));

		/* inject root URLs */
		List<String> urls = new ArrayList<String>();
		urls.add("http://www.163.com/");
		urls.add("http://www.sohu.com/");
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

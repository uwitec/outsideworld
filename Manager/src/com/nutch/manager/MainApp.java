package com.nutch.manager;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.crawl.Crawl;

import com.model.Site;

public class MainApp {

	private static Context context = new Context();

	/* 启动 Crawl */
	private static int startCrawl() throws Exception {
		// clear crawl DB
		FileUtils.cleanDirectory(new File(context.getCrawlDB()));

		/* inject root URLs */
		List<String> urls = new LinkedList<String>();
		for (Site site : context.getSites()) {
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

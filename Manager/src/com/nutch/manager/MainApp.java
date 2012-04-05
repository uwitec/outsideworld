package com.nutch.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.crawl.Crawl;

public class MainApp {

	private static Context context = new Context();

	private static int startNutch() throws Exception {
		// clear crawl DB
		FileUtils.cleanDirectory(new File(context.getNutchCrawlDB()));

		/* inject root URLs */
		List<String> urls = new ArrayList<String>();
		urls.add("http://www.163.com/");
		urls.add("http://www.sohu.com/");
		new UrlInjector(context).initNutchUrls(urls);

		/* crawl parameters */
		String[] args = new String[9];
		args[0] = context.getNutchUrls();
		args[1] = "-dir";
		args[2] = context.getNutchCrawlDB();
		args[3] = "-threads";
		args[4] = context.getNutchThread();
		args[5] = "-depth";
		args[6] = context.getNutchDepth();
		args[7] = "-topN";
		args[8] = context.getNutchTopN();

		/* start Crawl */
		int rc = ToolRunner.run(context.getNutchConfig(), new Crawl(), args);
		return rc;
	}

	public static void main(String[] args) throws Exception {
		startNutch();
	}

}

package com.nutch.manager;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.crawl.Injector;
import org.apache.nutch.util.NutchConfiguration;


public class UrlInjector {

	private Context context;

	public UrlInjector(Context context) {
		this.context = context;
	}

	public void inject(List<String> urls) throws Exception {
		/* create new URL file */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		File tmpDir = new File(System.getenv("TMP"), sdf.format(new Date()));
		File urlFile = new File(tmpDir, "urls");
		if (!urlFile.getParentFile().exists()) {
			urlFile.getParentFile().mkdirs();
		}
		if (!urlFile.exists()) {
			urlFile.createNewFile();
		}

		/* write URLs to URL file */
		FileWriter fw = new FileWriter(urlFile);
		for (String url : urls) {
			fw.append(url);
			fw.append("\n");
		}
		fw.flush();
		fw.close();

		String[] args = new String[2];
		args[0] = context.getNutchCrawlDB();
		args[1] = tmpDir.getAbsolutePath();
		System.out.println(args[1]);
		Configuration conf = NutchConfiguration.create();
		int rc = ToolRunner.run(conf, new Injector(),
				args);
		if (rc != 0) {
			throw new Exception("Inject URLs error");
		}
	}

	public void initNutchUrls(List<String> urls) throws Exception {
		File urlDir = new File(context.getNutchUrlDir());

		/* remove old files */
		for (File file : urlDir.listFiles()) {
			file.delete();
		}

		/* create new URL file */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		File urlFile = new File(urlDir, sdf.format(new Date()));
		if (!urlFile.getParentFile().exists()) {
			urlFile.getParentFile().mkdirs();
		}
		if (!urlFile.exists()) {
			urlFile.createNewFile();
		}

		/* write URLs to URL file */
		FileWriter fw = new FileWriter(urlFile);
		for (String url : urls) {
			fw.append(url);
			fw.append("\n");
		}
		fw.flush();
		fw.close();
	}

	public static void main(String[] args) {
		Context context = new Context();
		UrlInjector urlInjector = new UrlInjector(context);

		List<String> urls = new ArrayList<String>();
		urls.add("http://www.163.com/");
		urls.add("http://www.sohu.com/");

		try {
			urlInjector.inject(urls);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

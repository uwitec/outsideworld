package com.nutch.manager;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.util.NutchConfiguration;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class Context {

	private Configuration nutchConfig = NutchConfiguration.create();

	private String nutchCrawlDB;
	private String nutchThread;
	private String nutchDepth;
	private String nutchTopN;
	private String nutchUrls;

	public Context(HibernateTemplate hibernateTemplate) {
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader()
					.getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		nutchCrawlDB = properties.getProperty("crawl.db");
		nutchUrls = properties.getProperty("crawl.urls");
		nutchThread = properties.getProperty("crawl.threads");
		nutchDepth = properties.getProperty("crawl.depth");
		nutchTopN = properties.getProperty("crawl.top");

		/* check parameters */
		if (!new File(nutchCrawlDB).exists()) {
			new File(nutchCrawlDB).mkdirs();
		}
		if (!new File(nutchUrls).exists()) {
			new File(nutchUrls).mkdirs();
		}

		/* load configuration from database */
		hibernateTemplate.find("from Site");
	}

	public String getNutchCrawlDB() {
		return nutchCrawlDB;
	}

	public Configuration getNutchConfig() {
		return nutchConfig;
	}

	public String getNutchThread() {
		return nutchThread;
	}

	public void setNutchThread(String nutchThread) {
		this.nutchThread = nutchThread;
	}

	public String getNutchDepth() {
		return nutchDepth;
	}

	public void setNutchDepth(String nutchDepth) {
		this.nutchDepth = nutchDepth;
	}

	public String getNutchTopN() {
		return nutchTopN;
	}

	public void setNutchTopN(String nutchTopN) {
		this.nutchTopN = nutchTopN;
	}

	public String getNutchUrls() {
		return nutchUrls;
	}

	public void setNutchUrls(String nutchUrls) {
		this.nutchUrls = nutchUrls;
	}

	public void setNutchConfig(Configuration nutchConfig) {
		this.nutchConfig = nutchConfig;
	}

	public void setNutchCrawlDB(String nutchCrawlDB) {
		this.nutchCrawlDB = nutchCrawlDB;
	}
}

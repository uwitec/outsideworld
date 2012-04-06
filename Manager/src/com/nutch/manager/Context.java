package com.nutch.manager;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.util.NutchConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class Context {

	private Configuration nutchConfig;
	private ApplicationContext context;
	private HibernateTemplate hibernateTemplate;

	private String crawlDB;
	private String crawlThread;
	private String crawlDepth;
	private String crawlTopN;
	private String crawlUrls;

	public Context(ApplicationContext context) {
		this.context = context;
		this.nutchConfig = NutchConfiguration.create();
		this.hibernateTemplate = this.context.getBean("hibernateTemplate",
				HibernateTemplate.class);

		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader()
					.getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		crawlDB = properties.getProperty("crawl.db");
		crawlUrls = properties.getProperty("crawl.urls");
		crawlThread = properties.getProperty("crawl.threads");
		crawlDepth = properties.getProperty("crawl.depth");
		crawlTopN = properties.getProperty("crawl.top");

		/* check parameters */
		if (!new File(crawlDB).exists()) {
			new File(crawlDB).mkdirs();
		}
		if (!new File(crawlUrls).exists()) {
			new File(crawlUrls).mkdirs();
		}

		/* load configuration from database */
		hibernateTemplate.find("from Site");
	}

	public String getCrawlDB() {
		return crawlDB;
	}

	public Configuration getNutchConfig() {
		return nutchConfig;
	}

	public String getCrawlThread() {
		return crawlThread;
	}

	public String getCrawlDepth() {
		return crawlDepth;
	}

	public String getCrawlTopN() {
		return crawlTopN;
	}

	public String getCrawlUrls() {
		return crawlUrls;
	}

}

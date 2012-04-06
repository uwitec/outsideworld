package com.nutch.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.util.NutchConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.model.Site;
import com.util.SpringFactory;

public class Context {

	private static Logger LOG = LoggerFactory.getLogger(Context.class);

	private Configuration nutchConfig;
	private HibernateTemplate hibernateTemplate;

	private String crawlDB;
	private String crawlThread;
	private String crawlDepth;
	private String crawlTopN;
	private String crawlUrls;

	private List<Site> sites = null;

	@SuppressWarnings("unchecked")
	public Context() {
		this.nutchConfig = NutchConfiguration.create();
		try {
			this.hibernateTemplate = SpringFactory.getBean("hibernateTemplate");
		} catch (Exception e) {
			LOG.error("Get HibernateTemplate error", e);
		}

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
		sites = hibernateTemplate.find("from Site");
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

	public List<Site> getSites() {
		return sites;
	}
}

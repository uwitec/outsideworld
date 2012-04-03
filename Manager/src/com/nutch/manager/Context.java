package com.nutch.manager;

import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.util.NutchConfiguration;

public class Context {

	private Configuration nutchConfiguration;

	private String nutchUrlDir;

	private String nutchCrawlDB;

	public Context() {
		nutchConfiguration = NutchConfiguration.create();
	}

	public String getNutchUrlDir() {
		return nutchUrlDir;
	}

	public String getNutchCrawlDB() {
		return nutchCrawlDB;
	}

	public Configuration getNutchConfiguration() {
		return nutchConfiguration;
	}
}

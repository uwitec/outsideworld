package com.search;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MetaSearcherClient {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"metasearchContext.xml");

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanid) {
		return (T) context.getBean(beanid);
	}

	public static void main(String[] args) {
		new Thread(new MetaSearcher()).start();
	}
}

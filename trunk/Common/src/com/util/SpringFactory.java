package com.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringFactory {

	private static ApplicationContext context = new FileSystemXmlApplicationContext(
			"conf/applicationContext.xml");

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanid) {
		return (T) context.getBean(beanid);
	}
}

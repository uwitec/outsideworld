package com.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFactory {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanid) throws Exception {
		return (T) context.getBean(beanid);
	}

	public static void setApplication(ApplicationContext cont) throws Exception {
		context = cont;
	}
}

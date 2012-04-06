package com.util;

import org.springframework.context.ApplicationContext;

public class SpringFactory {	
	private static ApplicationContext context;
    public static Object getBean(String beanid) throws Exception{
    	return null;
    }
    
    public static void setApplication(ApplicationContext cont)  throws Exception{
    	context = cont;
    }
}

package com.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {
    
    
    public static String getHost(String u){
    	URL url = null;
		try {
			url = new URL(u);
		} catch (MalformedURLException e) {
			return null;
		}
    	return url.getHost();
    }
    
}

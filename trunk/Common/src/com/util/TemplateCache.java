package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.model.policy.Template;

public class TemplateCache {

    private static Map<String,List<Template>> cache = new HashMap<String,List<Template>>();
    
    /**
     * 从cache中获得template
     * @param url
     * @return
     * @throws Exception
     */
    public static  Template getTemplate(String url) throws Exception{
    	String host = UrlUtils.getHost(url);
    	if(StringUtils.isBlank(host)){
    		return null;
    	}
    	List<Template> list = cache.get(host);
    	if(list!=null&&list.size()>0){
    		for(Template t:list){
    			Pattern pattern = Pattern.compile(t.getUrlRegex());
    			Matcher matcher = pattern.matcher(url);
    			if(matcher.find()){
    				return t;
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * 添加template到cache中
     * @param template
     */
    public static void addTemplate(Template template) {
    	if(cache.get(template.getDomain())==null){
    		List<Template> list = new ArrayList<Template>();
    		list.add(template);
    		cache.put(template.getDomain(), list);
    	}
    	else{
    		cache.get(template.getDomain()).add(template);
    	}
    }
}

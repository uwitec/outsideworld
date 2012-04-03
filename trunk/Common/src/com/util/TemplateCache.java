package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.model.Template;

public class TemplateCache {
	/**
	 * 使用domain作为key，template的列表作为value的cache,查询的时候需要将domain进行对比，取得Template
	 * 的列表，然后根据url和正则表达式进行查找
	 */
    private static Map<String,List<Template>> cache = new HashMap<String,List<Template>>();
    
    /**
     * 根据url查找相应的template
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
     * 将template加入cache中
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

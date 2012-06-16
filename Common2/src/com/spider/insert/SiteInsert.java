package com.spider.insert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.dao.CommonDAO;
import com.entity.Source;
import com.entity.Template;
import com.model.Page;
import com.util.SpringFactory;

public class SiteInsert implements Runnable {
    private CommonDAO commonDAO = SpringFactory.getBean("commonDAO");
    private ConcurrentLinkedQueue<Page> fetchQueue = SpringFactory.getBean("fetchQueue");
    private Map<Integer,Set<Template>> templateMap =  SpringFactory.getBean("templateMap");
	@Override
	public void run() {
		while(true){
		List<Source> sources = commonDAO.getAll(Source.class);
		Page page = null;
		for (Source source : sources) {
			URL url = null;
			try {
				url = new URL(source.getUrl());
			} catch (MalformedURLException e) {
				continue;
			}
			templateMap.put(source.getId(), source.getTempaltes());
			page = new Page();
			page.setUrl(url);
			page.setDepth(0);
			page.setSource(source);
			fetchQueue.add(page);
		}
		try {
			Thread.sleep(24*1000*3600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

}

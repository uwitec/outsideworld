package com.spider.extract;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.entity.Template;
import com.model.Page;

public class ExtractControl implements Runnable {
    private List<Extractor> extractorChain;
    private ConcurrentLinkedQueue<Page> extractQueue;
    private Map<Integer,Set<Template>> templateMap; 
	
	@Override
	public void run() {
		while(true){
			Page page = extractQueue.poll();
			try{
			if(page == null){
				System.out.println("Extract thread sleep!");
				Thread.sleep(1*1000);
			}
			else{
				for(Extractor extractor:extractorChain){
					int k = extractor.extract(page,templateMap.get(page.getSource().getId()));
					if(k==-1){
						break;
					}
				}
			}
			}catch(Exception e){
				
			}
		}

	}

	public List<Extractor> getExtractorChain() {
		return extractorChain;
	}

	public void setExtractorChain(List<Extractor> extractorChain) {
		this.extractorChain = extractorChain;
	}

	public ConcurrentLinkedQueue<Page> getExtractQueue() {
		return extractQueue;
	}

	public void setExtractQueue(ConcurrentLinkedQueue<Page> extractQueue) {
		this.extractQueue = extractQueue;
	}

	public Map<Integer, Set<Template>> getTemplateMap() {
		return templateMap;
	}

	public void setTemplateMap(Map<Integer, Set<Template>> templateMap) {
		this.templateMap = templateMap;
	}
	
	
	
}

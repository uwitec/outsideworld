package com.spider.filter;

import java.util.Date;
import java.util.Map;

import com.model.Page;

public class IntervalFilter implements Filter {
	private Map<String,Long> lastUpdateMap;
	@Override
	public boolean filter(Page item) throws Exception {
		Integer interval = item.getSource().getInterval();
		if(lastUpdateMap.get(item.getSource().getUrl())==null||System.currentTimeMillis()-lastUpdateMap.get(item.getSource().getUrl())>interval*3600*1000){
			lastUpdateMap.put(item.getSource().getUrl(), new Date().getTime());
			return true;
		}
		return false;
	}
	
    
	public Map<String, Long> getLastUpdateMap() {
		return lastUpdateMap;
	}


	public void setLastUpdateMap(Map<String, Long> lastUpdateMap) {
		this.lastUpdateMap = lastUpdateMap;
	}
}

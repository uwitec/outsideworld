package com.spider.filter;

import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.algorithm.BloomFilter;
import com.model.Page;

public class IntervalFilter implements Filter {
	private Map<String, Long> lastUpdateMap;
	private BloomFilter bloomFilter;

	@Override
	public boolean filter(Page item) throws Exception {
		if (!bloomFilter.contains(item.getUrl().toString())) {
			bloomFilter.add(item.getUrl().toString());
			return true;
		}
		Integer interval = item.getSource().getInterval();
		String domain = item.getDomain();
		if (StringUtils.isBlank(domain)) {
			domain = new URL(item.getSource().getUrl()).getHost();
		}
		if (lastUpdateMap.get(domain) == null
				|| System.currentTimeMillis() - lastUpdateMap.get(domain) > interval * 3600 * 1000) {
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

	public BloomFilter getBloomFilter() {
		return bloomFilter;
	}

	public void setBloomFilter(BloomFilter bloomFilter) {
		this.bloomFilter = bloomFilter;
	}
}

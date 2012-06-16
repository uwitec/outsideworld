package com.spider.filter;

import com.algorithm.BloomFilter;
import com.model.Page;


public class DuplicatedUrlFilter implements Filter {
    private BloomFilter bloomFilter;
    @Override
    public boolean filter(Page item) throws Exception {
        long start = System.currentTimeMillis();
        String url = item.getUrl().toString();
        if(bloomFilter.contains(url)){
            return false;
        }
        bloomFilter.add(url);
        long stop = System.currentTimeMillis();
        System.out.println("bloomfilter:"+(stop-start));
        return true;
    }
    public BloomFilter getBloomFilter() {
        return bloomFilter;
    }
    public void setBloomFilter(BloomFilter bloomFilter) {
        this.bloomFilter = bloomFilter;
    }
}

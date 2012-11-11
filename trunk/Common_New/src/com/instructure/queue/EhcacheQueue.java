package com.instructure.queue;

import org.springframework.cache.ehcache.EhCacheCacheManager;;

public class EhcacheQueue<T> implements Queue<T> {

	private EhCacheCacheManager cacheManeger;
	
	@Override
	public T poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void push(T t) {
		// TODO Auto-generated method stub

	}

	public EhCacheCacheManager getCacheManeger() {
		return cacheManeger;
	}

	public void setCacheManeger(EhCacheCacheManager cacheManeger) {
		this.cacheManeger = cacheManeger;
	}

}

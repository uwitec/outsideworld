package com.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.CommonDAO;

public class CacheStore {

	private static CommonDAO commonDAO;

	private int interval = 10;

	private static Map<String, Cache> cacheMap = new HashMap<String, Cache>();

	public CacheStore() {
		new CacheThread().start();
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		CacheStore.commonDAO = commonDAO;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setConfig(List<String[]> list) {
		for (String[] strs : list) {
			String name = strs[0];
			String sql = strs[1];
			int interval = Integer.parseInt(strs[2]);
			regist(name, sql, interval);
		}
	}

	private void regist(String name, String sql, int interval) {
		Cache cache = new Cache(sql, interval);
		cache.refresh();
		cacheMap.put(name, cache);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> get(String name) {
		if (cacheMap.containsKey(name)) {
			return (List<T>) cacheMap.get(name).list;
		} else {
			return null;
		}
	}

	private static class Cache {

		public List<?> list;

		public String sql;

		public int interval;

		public long lastupdate;

		public Cache(String sql, int interval) {
			this.sql = sql;
			this.interval = interval;
		}

		public void refresh() {
			list = commonDAO.query(sql);
			lastupdate = System.currentTimeMillis();
		}
	}

	private class CacheThread extends Thread {
		public void run() {
			while (true) {
				for (Cache set : cacheMap.values()) {
					try {
						if (System.currentTimeMillis() - set.lastupdate > set.interval * 1000) {
							set.refresh();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(interval * 1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}

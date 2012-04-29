package com.weibo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.model.Item;

public abstract class AbstractWeiboClient<T> implements Runnable {

	private Set<Object> cache = new HashSet<Object>();
	private int MAX_RETRY = 5;

	@Override
	public void run() {
		List<T> weibos = null;
		List<Item> items = null;
		try {
			login();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int retry = 0;
		while (true) {
			try {
				weibos = getWeibos();
				retry = 1;
			} catch (Exception e) {
				e.printStackTrace();
				if (retry < MAX_RETRY) {
					retry++;
					try {
						login();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					continue;
				} else {
					break;
				}
			}
			try {
				items = sortItems(weibos);
				items = filterItem(items);
				saveItems(items);
				items = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (getInterval() > 0) {
				try {
					Thread.sleep(getInterval() * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public abstract Object getIdentifier(T weibo);

	/* 登录微博客户端 */
	public abstract void login() throws Exception;

	/* 去掉重复的微博 */
	public List<Item> sortItems(List<T> newItems) {
		List<Item> list = new ArrayList<Item>();

		if (newItems == null) {
			return list;
		}

		if (cache.size() < 1) {
			for (T newItem : newItems) {
				list.add(wrapItem(newItem));
				cache.add(getIdentifier(newItem));
			}
			return list;
		}

		for (T newItem : newItems) {
			if (!cache.contains(getIdentifier(newItem))) {
				list.add(wrapItem(newItem));
			}
		}

		/* 重置缓存 */
		cache.clear();
		for (T newItem : newItems) {
			cache.add(getIdentifier(newItem));
		}
		return list;
	}

	/* 封装微博 */
	public abstract Item wrapItem(T weibo);

	/* 取得最新的微博 */
	public abstract List<T> getWeibos() throws Exception;

	/* 过滤和关键字有关的微博 */
	public abstract List<Item> filterItem(List<Item> weibos);

	/* 保存微博 */
	public abstract void saveItems(List<Item> items) throws Exception;

	/* 抓取间隔，单位：秒 */
	public abstract int getInterval();
}

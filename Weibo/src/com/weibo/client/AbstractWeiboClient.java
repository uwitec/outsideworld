package com.weibo.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

import com.dao.ItemDao;
import com.model.Item;
import com.weibo.WeiboClient;
import com.weibo.WeiboFilter;

public abstract class AbstractWeiboClient<T> implements Runnable {

	private static Logger LOG = Logger.getLogger(AbstractWeiboClient.class);

	private Set<Serializable> cache;

	protected ItemDao itemDAO = WeiboClient.getBean("itemDao");

	private int MAX_RETRY = 5;

	protected String[] params;

	public AbstractWeiboClient(String[] params) {
		this.params = params;
		cache = getCache();
	}

	@Override
	public void run() {
		List<T> weibos = null;
		List<Item> items = null;
		try {
			login();
		} catch (Exception e) {
			LOG.error("Login weibo error", e);
		}
		int retry = 0;
		while (true) {
			try {
				weibos = getWeibos();
				retry = 1;
			} catch (Exception e) {
				LOG.error("Get Weibo error", e);
				if (retry < MAX_RETRY) {
					retry++;
					try {
						login();
					} catch (Exception ex) {
						LOG.error("Login weibo error", e);
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
				LOG.error("Handle weibo error", e);
			}
			if (getInterval() > 0) {
				try {
					Thread.sleep(getInterval() * 1000);
				} catch (InterruptedException e) {
					// ignore
				}
			}
		}
	}

	public abstract Set<Serializable> getCache();

	public abstract Lock getLock();

	public abstract Serializable getIdentifier(T weibo);

	/* 登录微博客户端 */
	public abstract void login() throws Exception;

	/* 去掉重复的微博 */
	public List<Item> sortItems(List<T> newItems) {
		List<Item> list = new ArrayList<Item>();
		Lock lock = getLock();
		try {
			lock.lock();
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
		} catch (Exception e) {
			LOG.error("Remove duplicated weibo error", e);
		} finally {
			lock.unlock();
		}
		LOG.info("Pickup " + list.size() + " valid weibos");
		return list;
	}

	/* 过滤和关键字有关的微博 */
	public List<Item> filterItem(List<Item> weibos) {
		LOG.info("Filter from " + weibos.size() + " weibos");
		for (int i = 0; i < weibos.size(); i++) {
			if (!WeiboFilter.isValid(weibos.get(i))) {
				weibos.remove(i);
				i--;
			}
		}
		return weibos;
	}

	/* 保存微博 */
	public void saveItems(List<Item> items) throws Exception {
		LOG.info("Insert " + items.size() + " weibos");
		for (Item item : items) {
			item.setType("weibo");
			item.setTitle(item.getContent());
			item.setCrawlTime(new Date());
			itemDAO.insert(item);
		}
	}

	/* 封装微博 */
	public abstract Item wrapItem(T weibo);

	/* 取得最新的微博 */
	public abstract List<T> getWeibos() throws Exception;

	/* 抓取间隔，单位：秒 */
	public abstract int getInterval();
}

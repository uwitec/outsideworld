package com.weibo;

import java.util.ArrayList;
import java.util.List;

import com.model.Item;

public abstract class AbstractWeiboClient<T> implements Runnable {

	@Override
	public void run() {
		List<T> lastWeibos = null;
		List<T> newWeibos = null;
		List<Item> items = null;
		while (true) {
			try {
				newWeibos = getWeibos();
				// 抓取微薄异常,重新登陆
				if (newWeibos == null) {
					login();
				}
				items = sortItems(newWeibos, lastWeibos);
				items = filterItem(items);
				saveItems(items);
				items = null;
				lastWeibos = newWeibos;
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(getInterval() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/* 登录微博客户端 */
	public abstract void login() throws Exception;

	/* 去掉重复的微博 */
	public List<Item> sortItems(List<T> newItems, List<T> oldItems) {
		List<Item> list = new ArrayList<Item>();

		if (newItems == null || newItems.size() < 1) {
			return list;
		}

		if (oldItems == null) {
			for (T newItem : newItems) {
				list.add(wrapItem(newItem));
			}
			return list;
		}

		boolean single = true;
		for (T newItem : newItems) {
			single = false;
			for (T oldItem : oldItems) {
				if (isSame(newItem, oldItem)) {
					single = false;
					break;
				}
			}
			if (single) {
				list.add(wrapItem(newItem));
			}
		}
		return list;
	}

	/* 判断两条微博是是同一条微博 */
	public abstract boolean isSame(T weibo1, T weibo2);

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

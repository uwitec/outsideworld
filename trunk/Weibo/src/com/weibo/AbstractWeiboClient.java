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
				saveItems(items);
				lastWeibos = newWeibos;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public abstract void login() throws Exception;

	public List<Item> sortItems(List<T> newItems, List<T> oldItems) {
		List<Item> list = new ArrayList<Item>();

		if (newItems == null || newItems.size() < 1) {
			return list;
		}
		newItems = filterItem(newItems);

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

	public abstract boolean isSame(T weibo1, T weibo2);

	public abstract Item wrapItem(T weibo);

	public abstract List<T> getWeibos() throws Exception;

	public abstract List<T> filterItem(List<T> weibos);

	public abstract void saveItems(List<Item> items) throws Exception;
}

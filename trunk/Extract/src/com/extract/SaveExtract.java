package com.extract;

import com.dao.ItemDao;
import com.model.Item;

public class SaveExtract implements Extract {
	private ItemDao itemDao;

	@Override
	public void process(Item item) throws Exception {
		if (item.isStatus()) {
			itemDao.insert(item);
		}
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

}

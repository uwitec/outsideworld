package com;

import java.util.List;
import com.dao.ItemDao;
import com.index.Index;
import com.model.Item;


public class ItemIndexer {
    private ItemDao itemDao;
    private Index index;
    public void index() throws Exception{
        List<Item> items = null;
        index.open(indexDir);
        //每次针对10W条数据建立索引,如果全量数据不够10W，则对全部建立索引
        int i=0;
        do{
            items = itemDao.poll(1000);
            index.index(items);
        }while(items!=null&&items.size()>0&&i++<100);
        index.close();
    }
    public ItemDao getItemDao() {
        return itemDao;
    }
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}

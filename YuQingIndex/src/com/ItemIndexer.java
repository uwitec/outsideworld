package com;

import java.util.List;

import com.dao.ItemDao;
import com.index.AbstractIndex;
import com.model.Item;
import com.util.SpringFactory;


public class ItemIndexer {
    private ItemDao itemDao;
    private AbstractIndex indexImpl;
    
    public AbstractIndex getIndexImpl() {
        return indexImpl;
    }
    
    public void setIndexImpl(AbstractIndex indexImpl) {
        this.indexImpl = indexImpl;
    }
    public void index() throws Exception{
        List<Item> items = null;
        indexImpl.open("/home/fangxia722/index");
        //每次针对10W条数据建立索引,如果全量数据不够10W，则对全部建立索引
        int i=0;
        do{
            items = itemDao.poll(1000,i*1000);
            indexImpl.index(items);
            indexImpl.commit();
        }while(items!=null&&items.size()>0&&i++<100);
        indexImpl.close();
    }
    public ItemDao getItemDao() {
        return itemDao;
    }
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
    
    public static void main(String[] args) throws Exception{
    	ItemIndexer indexer = (ItemIndexer)SpringFactory.getBean("itemIndexer");
    	indexer.index();
    }
}

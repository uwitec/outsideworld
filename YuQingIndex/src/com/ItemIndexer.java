package com;

import java.util.List;
import org.apache.log4j.Logger;
import com.dao.ItemDao;
import com.index.AbstractIndex;
import com.model.Item;

public class ItemIndexer {
    private static Logger LOG = Logger.getLogger(ItemIndexer.class);
    private ItemDao itemDao;
    private AbstractIndex indexImpl;
    private boolean isOpen = false;

    public void index(List<Item> items, List<Item> dels, String dir) throws Exception {
        try {
            if (!isOpen) {
                LOG.info("Open the directory:"+dir);
                indexImpl.open(dir);
                isOpen = true;
            }
            LOG.debug("Delete the items in mongo,the num is "+dels.size());
            itemDao.remove(dels);
            LOG.debug("Delete success!");
            LOG.debug("Index the items in disk,the num is"+items.size());
            indexImpl.index(items);
            indexImpl.commit();
            LOG.debug("Index success!");
        } catch (Exception e) {
            LOG.info("There is exception accur,so close indexWriter!");
            indexImpl.close();
            LOG.info(e);
            throw new Exception(e);
        }
    }

    public AbstractIndex getIndexImpl() {
        return indexImpl;
    }

    public void setIndexImpl(AbstractIndex indexImpl) {
        this.indexImpl = indexImpl;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}

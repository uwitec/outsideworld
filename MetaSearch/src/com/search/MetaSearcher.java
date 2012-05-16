package com.search;

import java.util.List;
import com.dao.ItemDao;
import com.extract.Extract;
import com.model.Item;
import com.model.policy.Meta;
import com.model.policy.Topic;
import com.util.Fetcher;


public class MetaSearcher {
    private Fetcher fetcher;
    private ItemDao itemDao;
    private Extract extract;
    public void metaSearch(List<Topic> topics,List<Meta> metas) throws Exception {
        for(Meta meta:metas){
            for(Topic topic:topics){
                String query = topic.getInclude();
                query.replaceAll(";", "+");               
                List<String> urls = meta.constructUrl(query);
                for(String url:urls){
                    Item item = new Item();
                    item.setUrl(url);
                    fetcher.fetch(item);
                    extract.process(item);
                    itemDao.insert(item);
                }
            }
        }
    }
    public Fetcher getFetcher() {
        return fetcher;
    }
    public void setFetcher(Fetcher fetcher) {
        this.fetcher = fetcher;
    }
    public ItemDao getItemDao() {
        return itemDao;
    }
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
    public Extract getExtract() {
        return extract;
    }
    public void setExtract(Extract extract) {
        this.extract = extract;
    }
}

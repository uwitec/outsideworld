package com;

import java.util.List;
import com.dao.ItemDao;
import com.model.Item;
import com.search.Search;


public class ItemSelector {
    private Search search;
    
    public void select() throws Exception{
        
    }
    public Search getSearch() {
        return search;
    }
    public void setSearch(Search search) {
        this.search = search;
    }
    
}

package com.api.fetch;

import com.extract.Extract;
import com.model.Item;
import com.util.SpringFactory;


public class FectchApi {
    private Fetcher fetcher;

    public Fetcher getFetcher() {
        return fetcher;
    }

    public void setFetcher(Fetcher fetcher) {
        this.fetcher = fetcher;
    }
    
    public void fecth(Item item) throws Exception{
        fetcher.fetch(item);
        Extract extractChain = SpringFactory.getBean("extractChain");
        extractChain.process(item);
    }
}

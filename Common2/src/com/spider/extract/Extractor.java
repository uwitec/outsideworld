package com.spider.extract;

import java.util.List;
import com.entity.Template;
import com.model.Item;
import com.model.Page;


public interface Extractor {
    public Item extract(Page page,List<Template> templates) throws Exception;
}

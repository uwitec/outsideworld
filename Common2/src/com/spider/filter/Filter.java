package com.spider.filter;

import com.model.Page;


public interface Filter {
    /**
     * 判断page中的url是否合法
     * @param page
     * @param host 主url的host
     * @return
     * @throws Exception
     */
    public boolean filter(Page item) throws Exception;
}

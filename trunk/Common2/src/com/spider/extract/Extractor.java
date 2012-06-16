package com.spider.extract;

import java.util.Set;

import com.entity.Template;
import com.model.Page;


public interface Extractor {
	/**
	 * 
	 * @param page
	 * @param templates
	 * @return 返回-1，证明抽取结束，否则返回0，表示进行下一步抽取
	 * @throws Exception
	 */
    public int extract(Page page,Set<Template> templates) throws Exception;
}

package com.extract;
import org.apache.commons.lang.StringUtils;

import com.model.Item;


public class ContentExtract extends AExtract {

	@Override
	public void process(Item item) throws Exception {
        if(item.getTemplate()==null){
        	item.setStatus(false);
        	return;
        }
        String content = extract("content",item);
        //采用默认的抽取方法进行抽取
        if(StringUtils.isBlank(content)){
        	
        }
        item.setContent(content);
	}
	
	

}

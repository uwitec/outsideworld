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
        //����Ĭ�ϵĳ�ȡ�������г�ȡ
        if(StringUtils.isBlank(content)){
        	
        }
        item.setContent(content);
	}
	
	

}

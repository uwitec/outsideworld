package com.extract;

import org.apache.commons.lang.StringUtils;

import com.model.Item;

public class TitleExtract extends AExtract {

	@Override
	public void process(Item item) throws Exception {
		 if(item.getTemplate()==null){
	        	item.setStatus(false);
	        	return;
	        }
	        String title = extract("title",item);
	        //如果没有抽取到，则使用默认的抽取策略
	        if(StringUtils.isBlank(title)){
	        	
	        }
	        item.setTitle(title);
	}

}

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
	        //����Ĭ�ϵĳ�ȡ�������г�ȡ
	        if(StringUtils.isBlank(title)){
	        	
	        }
	        item.setTitle(title);
	}

}

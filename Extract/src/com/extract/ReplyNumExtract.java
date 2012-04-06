package com.extract;

import org.apache.commons.lang.StringUtils;

import com.model.Item;

public class ReplyNumExtract extends AExtract {

	@Override
	public void process(Item item) throws Exception {
		 if(item.getTemplate()==null){
	        	item.setStatus(false);
	        	return;
	        }
	        String replyNum = extract("replyNum",item);
	        int reply = 0;
	        //如果没有抽取到，则使用默认的抽取策略
	        if(!StringUtils.isBlank(replyNum)&&StringUtils.isNumeric(replyNum)){
	        	reply = Integer.parseInt(replyNum);
	        }
	        item.setReplyNum(reply);
	}
	
}



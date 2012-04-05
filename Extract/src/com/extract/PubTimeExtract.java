package com.extract;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.model.Item;

public class PubTimeExtract extends AExtract {
	private Pattern regx1 = Pattern.compile("[\\d]-[\\d]-[\\d] [\\d]:[\\d]:[\\d]");
	private Pattern regx2 = Pattern.compile("[\\d]-[\\d]-[\\d] [\\d]");
	private Pattern regx3 = Pattern.compile("[\\d]年[\\d]月[\\d]日");

	@Override
	public void process(Item item) throws Exception {
		if(item.getTemplate()==null){
        	item.setStatus(false);
        	return;
        }
        String timeString = extract("pubTime",item);
        Date result = null;
        if(regx1.matcher(timeString).find()){
        	result = new Date(regx1.matcher(timeString).group()); 
        }
        else if(regx2.matcher(timeString).find()){
        	result = new Date(regx2.matcher(timeString).group()); 
        }
        else if(regx3.matcher(timeString).find()){
        	String temp = regx3.matcher(timeString).group();
        	temp.replace("年", "-");
        	temp.replace("月", "-");
        	temp.replace("日", "-");
        	result = new Date(temp); 
        }
        //如果没有抽取到，则使用默认的抽取策略
        if(StringUtils.isBlank(timeString)){
        	result = new Date();
        }
        
        item.setPubTime(result);
	}

	
	
}

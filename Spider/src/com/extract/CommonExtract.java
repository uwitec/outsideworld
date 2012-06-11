package com.extract;

import com.model.Item;
import com.model.rule.Element;


public class CommonExtract implements Extract {

    @Override
    public void extract(Item item) throws Exception {
        if(item.getTemplate()==null){
            return;
        }
        for(Element element:item.getTemplate().getElements()){
            
        }
    }
}

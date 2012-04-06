package com.extract;

import java.util.List;

import com.model.Item;

public class ExtractChain implements Extract {
    private List<Extract> extracts;
	@Override
	public void process(Item item) throws Exception {
		for(Extract extract:extracts){
			extract.process(item);
			if(!item.isStatus()){
				return;
			}
		}
	}
	public void setExtracts(List<Extract> extracts) {
		this.extracts = extracts;
	}
}

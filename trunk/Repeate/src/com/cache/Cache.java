package com.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	private Map<String, Integer> words = new HashMap<String, Integer>();

	public void addWord(String word,int w) {
		if (words.containsKey(word)) {
			words.put(word, words.get(word) + w);
		} else {
			words.put(word,w);
		}
	}
	
	public int getNum(String key){
		return words.get(key)!=null?words.get(key):0;
	}
	
	public void put(String word,int num){
		words.put(word, num);
	}
	
	public void clear(){
		words.clear();
	}
}

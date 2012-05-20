package com.model;

import java.math.BigDecimal;

public class WordItem implements Comparable{
    private String word;
    /**
     * 在集合中的次数
     */
    private int countInWords;
    /**
     * 在语料库中的次数
     */
    private int countInCorpus;
    /**
     * 比例
     */
    private double rate;
    /**
     * 在当前item中的次数
     */
    private int itemNum;
    
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getCountInWords() {
		return countInWords;
	}
	public void setCountInWords(int countInWords) {
		this.countInWords = countInWords;
	}
	public int getCountInCorpus() {
		return countInCorpus;
	}
	public void setCountInCorpus(int countInCorpus) {
		this.countInCorpus = countInCorpus;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public int compareTo(Object obj) {
		WordItem item = (WordItem)obj;		
		return -new Double(rate).compareTo(new Double(item.getRate()));
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
}

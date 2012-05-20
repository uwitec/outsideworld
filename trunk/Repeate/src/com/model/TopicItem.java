package com.model;

import java.util.List;

public class TopicItem implements Comparable{
    private String title;
    private String content;
    private String id;
    /**
     * 选出来的特征词语
     */
    private List<WordItem> words;
    /**
     * 原始的title数据
     */
    private List<String> rawTStrings;
    /**
     * 原始的content数据
     */
    private List<String> rawCStrings;
    private int label;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<WordItem> getWords() {
		return words;
	}
	public void setWords(List<WordItem> words) {
		this.words = words;
	}
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	public List<String> getRawTStrings() {
		return rawTStrings;
	}
	public void setRawTStrings(List<String> rawStrings) {
		this.rawTStrings = rawStrings;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getRawCStrings() {
		return rawCStrings;
	}
	public void setRawCStrings(List<String> rawCStrings) {
		this.rawCStrings = rawCStrings;
	}
	@Override
	public int compareTo(Object o) {
		TopicItem item=(TopicItem)o;
		return label-item.getLabel();
	}
	
	
}

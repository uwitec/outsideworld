package com.algorithm;

import java.util.ArrayList;
import java.util.List;
/**
 * 同心串算法
 * @author fangxia722
 */
public class ConcentricString {
	/**
	 * 将title转换为长度为2~concent长度的同心串
	 */
	private static final int concent = 5;
    public List<String> concentric(String title) throws Exception{
    	//根据句子之间的标点符号以及句子内的标点符号
    	List<String> arrays = new ArrayList<String>();
    	for(int i=0,k=0;i<title.length();i++){
    		if(isStop(title.charAt(i))&&k<i-1){
    			arrays.add(title.substring(k, i));
    			k=i+1;
    		}
    	}
    	if(arrays.size()==0){
    		arrays.add(title);
    	}
    	List<String> result = new ArrayList<String>();
    	for(String s:arrays){
    		for(int i=0;i<s.length();i++){
    			for(int j=2;j<concent&&i+j<=s.length();j++){
    				String word = s.substring(i, i+j);
    				result.add(word);
    			}
    		}
    	}
    	return result;
    }
    
    /**
     * 查看当前的字符c是不是句子分割符
     * @param c
     * @return
     */
    private boolean isStop(char c){
    	char[] stops = new char[]{',','.','。','《','》','，','<','>','-','“','”','\"'};
    	for(char s:stops){
    		if(c==s){
    			return true;
    		}
    	}
    	return false;
    }
}

package com.util;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class XPathUtil {
    private static HtmlCleaner htmlCleaner = new HtmlCleaner();
    
    public static String getResult(String input,String xpath) throws Exception{
    	TagNode node =htmlCleaner.clean(input);
    	TagNode[] nodes =(TagNode[])node.evaluateXPath(xpath);
    	if(nodes!=null&&nodes.length>0){
    		return nodes[0].getText().toString();
    	}
    	return "";
    }
    
    public static List<String> getResults(String input,String xpath) throws Exception{
    	List<String> result = new ArrayList<String>();
    	TagNode node =htmlCleaner.clean(input);
    	TagNode[] nodes =(TagNode[])node.evaluateXPath(xpath);
    	if(nodes!=null&&nodes.length>0){
    		for(TagNode n:nodes){
    			result.add(n.getText().toString());
    		}
    	}
    	return result;
    }
}

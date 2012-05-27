package com.test;

import java.io.StringReader;
import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 String str =
		 "核心提示：2012年5月22日，浙江丽水市云和县女副县长李某在县政府被人持刀劫持。9个小时后，李某获救。媒体称劫人者张某是上访户。一位当地人说，云和当地修建公厕，而公厕造到了张某的家门口。为此事，张某曾上访，其间因为辱骂县领导被拘留数日";
		 str = str.replaceAll("\\d{2,4}年\\d{1,2}月\\d{1,2}日", "----");
		 Analyzer analyzer = new PaodingAnalyzer();
		 String st = null;
		 StringBuffer sb = new StringBuffer();
		 StringReader reader = new StringReader(str);
		 TokenStream ts = analyzer.tokenStream("", reader);
		 TermAttribute termAtt = (TermAttribute)
		 ts.getAttribute(TermAttribute.class);
		 Token t = null;
		 try {
		 while (ts.incrementToken()) {
		 TermAttribute ta = ts.getAttribute(TermAttribute.class);
		 System.out.print(ta.term()+" ");
		 }
		 } catch (Exception e) {
		 }
//		for(int m=2010;m<2015;m++){
//		for (int i = 1; i < 13; i++) {
//			for (int j = 1; j < 32; j++) {
//			    String s = String.valueOf(i);
//			    String k = String.valueOf(j);
//				System.out.println(m+"-"+s+"-"+k);
//				System.out.println(m+"－"+s+"－"+k);
//			}
//		}
//		}
	}
}

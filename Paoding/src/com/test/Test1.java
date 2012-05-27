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
		String str = "你好，我是人，你好，我是猪";
		str = str.replaceAll("\\d{2,4}年\\d{1,2}月\\d{1,2}日", "----");
		Analyzer analyzer = new PaodingAnalyzer();
		StringReader reader = new StringReader(str);
		TokenStream ts = analyzer.tokenStream("", reader);
		TermAttribute termAtt = (TermAttribute) ts
				.getAttribute(TermAttribute.class);
		try {
			while (ts.incrementToken()) {
				TermAttribute ta = ts.getAttribute(TermAttribute.class);
				System.out.print(ta.term() + " ");
			}
		} catch (Exception e) {
		}
	}
}

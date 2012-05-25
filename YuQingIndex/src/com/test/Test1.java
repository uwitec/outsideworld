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
        String str = "核心提示：5月22日上午，浙江丽水市云和县女副县长李某在县政府被人持刀劫持。9个小时后，李某获救。媒体称劫人者张某是上访户。一位当地人说，云和当地修建公厕，而公厕造到了张某的家门口。为此事，张某曾上访，其间因为辱骂县领导被拘留数日";
        Analyzer analyzer = new PaodingAnalyzer();
        String st = null;
        StringBuffer sb = new StringBuffer();
        StringReader reader = new StringReader(str);
        TokenStream ts = analyzer.tokenStream("", reader);
        TermAttribute termAtt = (TermAttribute) ts.getAttribute(TermAttribute.class);
        Token t = null;
        try {
            while (ts.incrementToken()) {
                
                System.out.println(ts);
            }
        } catch (Exception e) {
        }
    }
}

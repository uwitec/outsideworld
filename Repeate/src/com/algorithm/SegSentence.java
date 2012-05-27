package com.algorithm;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.paoding.analysis.analyzer.PaodingAnalyzer;
import net.paoding.analysis.dictionary.Dictionary;
import net.paoding.analysis.dictionary.Hit;
import net.paoding.analysis.knife.CJKKnife;
import net.paoding.analysis.knife.Knife;
import net.paoding.analysis.knife.Paoding;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

public class SegSentence {
	private Analyzer analyzer = new PaodingAnalyzer();
	private static final Pattern dateF = Pattern
			.compile("\\d{2,4}年\\d{1,2}月\\d{1,2}日");

	public List<String> seg(String sentence) {
		// 将所有英文全部去掉
		// 预处理日期
		dateProcess(sentence);
		List<String> result = new ArrayList<String>();
		StringReader reader = new StringReader(sentence);
		TokenStream ts = analyzer.tokenStream("", reader);
		TermAttribute termAtt = (TermAttribute) ts
				.getAttribute(TermAttribute.class);
		try {
			while (ts.incrementToken()) {
				TermAttribute ta = ts.getAttribute(TermAttribute.class);
				String s = ta.term();
				if(s.length()<=1){
					continue;
				}
				else if(hit(s)){
					result.add(s);
				} else if (isNum(s)) {
					result.add(s);
				}
			}
		} catch (Exception e) {
		}
		return result;
	}

	private boolean hit(String word) {
		Paoding paoding = (Paoding) ((PaodingAnalyzer) analyzer).getKnife();
		if (paoding == null) {
			return false;
		}
		Knife[] knives = paoding.getKnives();
		for (Knife k : knives) {
			if (k instanceof CJKKnife) {
				Dictionary dictionary = ((CJKKnife) k).getVocabulary();
				Hit hit = dictionary.search(word, 0, word.length());
				if (hit.isHit()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isNum(String word) {
		if (StringUtils.isBlank(word)) {
			return false;
		}
		for (int i = 0; i < word.length(); i++) {
			if ((word.charAt(i) > '9' || word.charAt(i) < '0')
					&& word.charAt(i) != '.') {
				return false;
			}
		}
		return true;
	}

	private void dateProcess(String sentence){
		Matcher matcher = dateF.matcher(sentence);
		if(matcher.find()){
		for(int i=0;i<matcher.groupCount();i++){			
			sentence = sentence.replace(matcher.group(i), matcher.group(i).replace("年", "-").replace("月", "-").replace("日", "-"));
		}
		}
	}

	public static void main(String[] args) {
		SegSentence s = new SegSentence();
		s.hit("核心提示");
		s.isNum("23.5");
	}
}

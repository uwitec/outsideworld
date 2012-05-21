package com.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.cache.Cache;
import com.model.WordItem;
import com.util.SpringFactory;

public class KWordsSelector {
	/**
	 * 聚类的文本集合中的cache，记录文本集合中出现的词语以及次数
	 */
	private Cache wordsCache;
	/**
	 * 记录在本文档集合中词语的df值
	 */
	private Cache dfCache;
	/**
	 * 语料中的cache
	 */
	private Cache corpusCache;
	/**
	 * 表示当a成以上的文档都出现这个词语时，词语不具备区分能力，将这些词语去掉
	 */
	private static final double a = 1.0;
	// 选择的特征最多不能超过50个
	private static final int k = 100;
	// 根据wordsCache和corpusCache中出现的比例，大于0.4的词语选作当前词语的特征
	private static final double r = 0.4;

	/**
	 * @param words
	 *            所有同心串的词语
	 * @param len
	 *            传入的topic总的数目，在words中，若有些词在wordCacxhe中出现的次数大于a*len，则去掉
	 * @param topicStrings
	 *            topic中所包含的词语，应该去掉
	 * @return 选择出来可以做特征的词语，最多50个
	 */
	public List<WordItem> select(Set<String> words, int len,
			List<String> topicStrings) {
		// 首先去掉topicStrings中的词语
		if (topicStrings != null) {
			words.removeAll(topicStrings);
		}
		List<WordItem> result = new ArrayList<WordItem>();
		for (String s : words) {
			if (dfCache.getNum(s) > (a * len)) {
				System.out.println("太频繁的词语！" + s);
				continue;
			}
			int wordNum = wordsCache.getNum(s);
			int corpusNum = corpusCache.getNum(s);
			double rate = ((double) (wordNum + 1)) / ((double) (corpusNum + 1));
			WordItem wordItem = new WordItem();
			wordItem.setWord(s);
			wordItem.setCountInWords(wordNum + 1);
			wordItem.setCountInCorpus(corpusNum + 1);
			wordItem.setRate(rate);
			if (rate > r) {
				result.add(wordItem);
			}
		}
		// 排序，取前k个元素,若k大于整个词的一半，则选择整个词的一半为关键词
		Collections.sort(result);
		if (k < result.size()) {
				result = result.subList(0, k);
		}
		// for (WordItem i : result) {
		// System.out.println(i.getWord() + " " + i.getCountInWords() + " "
		// + i.getCountInCorpus());
		// }
		return result;
	}

	public Cache getCorpusCache() {
		return corpusCache;
	}

	public void setCorpusCache(Cache corpusCache) {
		this.corpusCache = corpusCache;
	}

	public Cache getWordsCache() {
		return wordsCache;
	}

	public void setWordsCache(Cache wordsCache) {
		this.wordsCache = wordsCache;
	}

	public Cache getDfCache() {
		return dfCache;
	}

	public void setDfCache(Cache dfCache) {
		this.dfCache = dfCache;
	}
}

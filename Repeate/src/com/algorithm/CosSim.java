package com.algorithm;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cache.Cache;
import com.model.TopicItem;
import com.model.WordItem;

/**
 * 
 * @author fangxia722
 * 
 */
public class CosSim implements Distance {
	private Cache wordsCache;

	/**
	 * 计算cos距离，根据所选择的特征，选取相同的词语， 将特征词语在TopicItem中出现的次数记录一下，
	 * title中出现的次数是content中的w倍， 作为topic中的特征值
	 */
	@Override
	public double distance(TopicItem f1, TopicItem f2) {
		Map<String, Double> f1Map = new HashMap<String, Double>();
		Map<String, Double> f2Map = new HashMap<String, Double>();
		// 记录x1、x2..等等f1中所有特征的平方
		double f1Sum2 = sum2(f1, f1Map);
		// 记录y1、y2..等等f2中所有特征的平方
		double f2Sum2 = sum2(f2, f2Map);
		// 记录x1*y1+x2*y2等等所有特征的平方
		double sum = 0.0;

		for (Map.Entry<String, Double> entry : f1Map.entrySet()) {
			if (f2Map.get(entry.getKey()) != null) {
				sum += entry.getValue() * f2Map.get(entry.getKey());
			}
		}
		return sum / (Math.sqrt(f1Sum2) * Math.sqrt(f2Sum2));

	}

	/**
	 * 计算f1所有特征的平方
	 * 
	 * @param f1
	 * @return
	 */
	private double sum2(TopicItem f1, Map<String, Double> map) {
		double result = 0.0;
		String title = f1.getTitle();
		String content = f1.getContent();
		for (WordItem wordItem : f1.getWords()) {
			String t = wordItem.getWord();			
			int s = 0;
			int k = 0;
			if (!StringUtils.isBlank(title)) {

				while ((k = title.indexOf(t, k)) >= 0) {
					// 在title中出现一次计算2次
					s += 2;
					// k向前移动
					k += t.length();
				}
			}
			if (!StringUtils.isBlank(content)) {
				k = 0;
				while ((k = content.indexOf(t, k)) >= 0) {
					// 在content中出现一次计算1次
					s += 1;
					// k向前移动
					k += t.length();
				}
			}
			//double idf = (double)s/wordsCache.getNum(t);
			double rate = wordItem.getRate();
			s*=smooth(rate);
			map.put(t, (double)s);
			wordItem.setItemNum(s);
			result += Math.pow(s, 2.0);
		}
		return result;
	}
	
	double smooth(double rate){
		if(rate>100){
			return 2.0;
		}
		else if(rate>20){
			return 1.5;
		}
		else{
			return 1.0;
		}
	}

	public Cache getWordsCache() {
		return wordsCache;
	}

	public void setWordsCache(Cache wordsCache) {
		this.wordsCache = wordsCache;
	}

}

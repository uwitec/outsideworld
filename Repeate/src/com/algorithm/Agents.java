package com.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.TopicItem;

public class Agents {
	private Distance distance;
	/**
	 * 距离的阀值
	 */
	private static final double o = 0.5;

	/**
	 * 聚类函数
	 * 
	 * @param topics
	 */
	public void clustering(List<TopicItem> topics) {
		// 若聚类的元素少于2个，返回
		if (topics == null || topics.size() < 2) {
			return;
		}
		// 定义map，将不同类别的TopicItem放入map的value中
		Map<Integer, List<TopicItem>> map = new HashMap<Integer, List<TopicItem>>();
		// 选取第一个元素，作为第一类
		List<TopicItem> class0 = new ArrayList<TopicItem>();
		class0.add(topics.get(0));
		int k=0;
		int l=0;
		map.put(k++, class0);		
		// 从第二个元素开始聚类
		for (int i = 1; i < topics.size(); i++) {
			double result = 0;
			List<TopicItem> value = null;
			for(Map.Entry<Integer, List<TopicItem>> cla:map.entrySet()){
				//计算出当前的topic和cla的平均距离
				double tmp = distance(topics.get(i),cla);
				//找到一个最相近的类
				if(result<tmp){
					result = tmp;
					value = cla.getValue();
					l=cla.getKey();
				}
			}	
			//如果相似度还是小于0。6，则必须新建分类
			if(result<o){
				List<TopicItem> newClass = new ArrayList<TopicItem>();
				newClass.add(topics.get(i));
				topics.get(i).setLabel(k);
				map.put(k++, newClass);	
			}
			//否则加入原有分类
			else{
				value.add(topics.get(i));
				topics.get(i).setLabel(l);
			}

		}

	}
	
	/**
	 * 计算一个item与已有分类的平均距离
	 * @param item
	 * @param cla
	 * @return
	 */
	private double distance(TopicItem item,Map.Entry<Integer, List<TopicItem>> cla){
		double result =0.0;
		for(TopicItem i:cla.getValue()){
			result+=distance.distance(item, i);
		}
		return result/(double)cla.getValue().size();
	}

	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance) {
		this.distance = distance;
	}
}

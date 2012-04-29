package com.weibo;

import java.util.List;

import com.algorithm.ConcentricString;
import com.model.Item;
import com.model.policy.Topic;
import com.util.CacheStore;
import com.util.SpringFactory;

public class WeiboFilter {

	private static CacheStore cache = SpringFactory.getBean("cache");

	public static boolean isValid(Item item) {
		try {
			List<String> list = new ConcentricString().concentric(item
					.getContent());
			List<Topic> topicList = cache.get("topic");
			for (Topic topic : topicList) {
				for (String txt : list) {
					if (topic.getMustHave().contains(txt)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

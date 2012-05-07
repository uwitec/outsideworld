package com.weibo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.algorithm.ConcentricString;
import com.dao.CommonDAO;
import com.model.Item;
import com.model.policy.Topic;
import com.util.SpringFactory;

public class WeiboFilter {

	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	private static Set<String> topic = new HashSet<String>();

	private static long lastupdate = 0;

	public static boolean isValid(Item item) {
		return true;
//		checkExpire();
//		try {
//			List<String> list = new ConcentricString().concentric(item
//					.getContent());
//			for (String word : list) {
//				if (topic.contains(word)) {
//					return true;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
	}

	private static void checkExpire() {
		if (System.currentTimeMillis() - lastupdate > 1000 * 3600) {
			List<Topic> list = commonDAO.query("from Topic");
			topic.clear();
			for (Topic t : list) {
				if (t.getInclude() != null) {
					String[] ss = t.getInclude().split(" ");
					for (String s : ss) {
						topic.add(s);
					}
				}
			}
			lastupdate = System.currentTimeMillis();
		}
	}
}

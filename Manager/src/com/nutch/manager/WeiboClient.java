package com.nutch.manager;

import java.util.List;

import com.dao.CommonDAO;
import com.model.policy.Param;
import com.util.SpringFactory;
import com.weibo.TencentWeiboUpdater;

public class WeiboClient {
	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	public static void main(String[] args) {
		// new Thread(new SinaWeiboUpdater("sina", 20, null)).start();

		List<Param> tencentParams = commonDAO
				.query("from Param p where p.type='tencentweibo'");
		for (Param param : tencentParams) {
			new Thread(new TencentWeiboUpdater("tencent", 5, new String[] {
					param.getValue1(), param.getValue2(), param.getValue3(),
					param.getValue4() })).start();
			break;
		}

		// List<Param> sohuParams = commonDAO
		// .query("from Param p where p.type='sohuweibo'");
		// for (Param param : sohuParams) {
		// new Thread(new SohuWeiboUpdater("sohu", 20, new String[] {
		// param.getValue1(), param.getValue2(), param.getValue3(),
		// param.getValue4() })).start();
		// break;
		// }
	}
}

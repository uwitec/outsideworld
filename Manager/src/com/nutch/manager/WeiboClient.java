package com.nutch.manager;

import java.util.List;

import com.dao.CommonDAO;
import com.model.policy.Param;
import com.util.SpringFactory;
import com.weibo.client.SinaWeiboClient;
import com.weibo.client.SohuWeiboClient;
import com.weibo.client.TencentWeiboClient;

public class WeiboClient {

	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	public static void main(String[] args) throws Exception {

		/* 新浪微博 */
		List<Param> sinaParams = commonDAO
				.query("from Param p where p.type='sinaweibo'");
		for (Param param : sinaParams) {
			new Thread(new SinaWeiboClient(new String[] { param.getValue1(),
					param.getValue2() })).start();
		}

		/* 腾讯微博 */
		List<Param> tencentParams = commonDAO
				.query("from Param p where p.type='tencentweibo'");
		for (Param param : tencentParams) {
			new Thread(new TencentWeiboClient(new String[] { param.getValue1(),
					param.getValue2(), param.getValue5() })).start();
		}

		/* 搜狐微博 */
		List<Param> sohuParams = commonDAO
				.query("from Param p where p.type='sohuweibo'");
		for (Param param : sohuParams) {
			new Thread(new SohuWeiboClient(new String[] { param.getValue1(),
					param.getValue2(), param.getValue3(), param.getValue4(),
					param.getValue5() })).start();
		}
	}
}

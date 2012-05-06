package com.nutch.manager;

import java.util.List;

import com.dao.CommonDAO;
import com.model.policy.Param;
import com.util.SpringFactory;
import com.weibo.SinaWeiboClient;
import com.weibo.SinaWeiboUpdater;
import com.weibo.SohuWeiboClient;
import com.weibo.SohuWeiboUpdater;
import com.weibo.TencentWeiboClient;
import com.weibo.TencentWeiboUpdater;

public class WeiboClient {

	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	public static void main(String[] args) throws Exception {

		/* 新浪微博 */
		List<Param> sinaParams = commonDAO
				.query("from Param p where p.type='sinaweibo'");
		for (Param param : sinaParams) {
			new Thread(new SinaWeiboClient(new String[] { param.getValue1() }))
					.start();
		}
		for (Param param : sinaParams) {
			new Thread(new SinaWeiboUpdater("sina", 20,
					new String[] { param.getValue1() })).start();
			break;
		}

		/* 腾讯微博 */
		List<Param> tencentParams = commonDAO
				.query("from Param p where p.type='tencentweibo'");
		for (Param param : tencentParams) {
			new Thread(new TencentWeiboClient(new String[] { param.getValue1(),
					param.getValue2() })).start();
		}
		for (Param param : tencentParams) {
			new Thread(new TencentWeiboUpdater("tencent", 5, new String[] {
					param.getValue1(), param.getValue2(), param.getValue3(),
					param.getValue4() })).start();
			break;
		}

		/* 搜狐微博 */
		List<Param> sohuParams = commonDAO
				.query("from Param p where p.type='sohuweibo'");
		for (Param param : sohuParams) {
			new Thread(new SohuWeiboClient(new String[] { param.getValue1(),
					param.getValue2(), param.getValue3(), param.getValue4() }))
					.start();
		}
		for (Param param : sohuParams) {
			new Thread(new SohuWeiboUpdater("sohu", 20, new String[] {
					param.getValue1(), param.getValue2(), param.getValue3(),
					param.getValue4() })).start();
			break;
		}
	}
}

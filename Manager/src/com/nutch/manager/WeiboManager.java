package com.nutch.manager;

import com.weibo.SinaWeiboClient;
import com.weibo.TencentWeiboClient;

public class WeiboManager {
	public static void main(String[] args) throws Exception {
		new Thread(new SinaWeiboClient()).start();
		new Thread(new TencentWeiboClient()).start();
	}
}

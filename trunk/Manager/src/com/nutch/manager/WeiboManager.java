package com.nutch.manager;

import com.weibo.SinaWeiboClient;

public class WeiboManager {
	public static void main(String[] args) throws Exception {
		new Thread(new SinaWeiboClient()).start();
	}
}

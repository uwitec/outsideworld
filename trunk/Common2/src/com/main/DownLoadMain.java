package com.main;

import com.download.Engine;
import com.util.SpringFactory;

public class DownLoadMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Engine engine = SpringFactory.getBean("downLoadEngine");
		engine.excute();
	}

}

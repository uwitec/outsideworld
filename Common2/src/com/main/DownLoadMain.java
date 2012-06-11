package com.main;

import com.dao.StoryDao;
import com.download.Engine;
import com.model.Story;
import com.util.SpringFactory;

public class DownLoadMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Story story = new Story();
		story.setCategory("picture");
		story.setDescription("你好，非常好的图片");
		story.setDownLoad(false);
		story.setDownloadUrl("http://imguxv.penshow.cn/uploadfile/2010/04/19/thumb/thumb_600_0_20100419050402434.jpg");
		StoryDao dao = SpringFactory.getBean("storyDao");
		dao.insert(story);
		Engine engine = SpringFactory.getBean("downLoadEngine");
		engine.excute();
	}

}

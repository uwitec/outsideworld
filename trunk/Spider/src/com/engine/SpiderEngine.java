package com.engine;

import com.dao.CommonDAO;
import com.entity.Element;
import com.entity.Source;
import com.entity.Template;
import com.extract.Extractor;
import com.spider.Spider;
import com.util.SpringFactory;

public class SpiderEngine {

	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	private int spiderNum = 3;
	private int extractorNum = 5;

	private SpiderEngine() {

		setup();

		/* Start spiders */
		Thread[] spiders = new Thread[spiderNum];
		for (int i = 0; i < spiderNum; i++) {
			spiders[i] = new Spider();
			spiders[i].start();
		}

		/* Start extractors */
		Thread[] extractors = new Thread[extractorNum];
		for (int i = 0; i < extractorNum; i++) {
			extractors[i] = new Extractor();
			extractors[i].start();
		}
	}

	private void setup() {
		commonDAO.update("delete from Element");
		commonDAO.update("delete from Template");
		commonDAO.update("delete from Source");

		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//h1[@id='h1title']");

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@id='endText']");

		Element e3 = new Element();
		e3.setName("pubTime");
		e3.setDefine("//div[@class='endContent']/span");

		Template t1 = new Template();
		t1.setDomain("news.163.com");
		t1.setUrlRegex("^http://news.163.com/\\d+/\\d+/\\d+/\\w+.html");
		t1.setType("Image");
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);

		Source s1 = new Source();
		s1.setName("网易新闻");
		s1.setUrl("http://news.163.com/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public static void main(String[] args) {
		new SpiderEngine();
	}

}

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
		e1.setDefine("//div[@class='contter']/h1/a");

		Element e2 = new Element();
		e2.setName("download");
		e2.setDefine("//li[@class='download']/a/@href");

		Template t1 = new Template();
		t1.setDomain("vector.penshow.cn");
		t1.setUrlRegex("^http://vector.penshow.cn/\\d+.shtml");
		t1.setType("Image");
		t1.getElements().add(e1);
		t1.getElements().add(e2);

		Source s1 = new Source();
		s1.setName("笔秀网");
		s1.setUrl("http://vector.penshow.cn/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public static void main(String[] args) {
		new SpiderEngine();
	}

}

package com.engine;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.CommonDAO;
import com.entity.Element;
import com.entity.Source;
import com.entity.Template;
import com.extract.Extractor;
import com.spider.Spider;

public class SpiderEngine {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"SpiderContext.xml");

	private static CommonDAO commonDAO = SpiderEngine.getBean("commonDAO");

	private int spiderNum = 1;
	private int extractorNum = 1;

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanid) {
		return (T) context.getBean(beanid);
	}

	private SpiderEngine() {

//		clear();
//		test1();
//		test2();

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

	private void clear() {
		commonDAO.update("delete from Element");
		commonDAO.update("delete from Template");
		commonDAO.update("delete from Source");
	}

	private void test1() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//div[@class='contter']/h1/a");

		Element e2 = new Element();
		e2.setName("download");
		e2.setDefine("//li[@class='download']/a/@href");

		Element e3 = new Element();
		e3.setName("thumb");
		e3.setDefine("//ul[@class='ul']/p/img/@src");

		Template t1 = new Template();
		t1.setDomain("vector.penshow.cn");
		t1.setUrlRegex("^http://vector.penshow.cn/\\d+.shtml");
		t1.setType("Image");
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);

		Source s1 = new Source();
		s1.setName("笔秀网");
		s1.setUrl("http://vector.penshow.cn/");
		s1.getTempaltes().add(t1);
		s1.setChannel("sucai");
		s1.setFormat("pic");

		commonDAO.save(s1);
	}

	private void test2() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//div[@class='font_box_2 ce']/h2");

		Element e2 = new Element();
		e2.setName("download");
		e2.setDefine("//div[@class='font_box_2 ce']/img/@src");

		Element e3 = new Element();
		e3.setName("thumb");
		e3.setDefine("//div[@class='font_box_2 ce']/img/@src");

		Template t1 = new Template();
		t1.setDomain("www.ttcnn.com");
		t1.setUrlRegex("^http://www.ttcnn.com/vector/vector/\\d+.html");
		t1.setType("Image");
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);

		Source s1 = new Source();
		s1.setName("天天素材");
		s1.setUrl("http://www.ttcnn.com/vector/");
		s1.getTempaltes().add(t1);
		s1.setChannel("sucai");
        s1.setFormat("pic");
		commonDAO.save(s1);
	}

	public static void main(String[] args) {
		new SpiderEngine();
	}

}

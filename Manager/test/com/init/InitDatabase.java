package com.init;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dao.CommonDAO;
import com.model.Element;
import com.model.Site;
import com.model.Template;
import com.util.SpringFactory;

public class InitDatabase {

	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	@BeforeClass
	public static void beforeClass() {
		/* clear sites */
		commonDAO.update("delete from Element");
		commonDAO.update("delete from Template");
		commonDAO.update("delete from Site");
	}

	@Test
	public void test163() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//h1[@id='h1title']");
		e1.setType("Xpath");

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@id='endText']");
		e2.setType("Xpath");

		Template t1 = new Template();
		t1.setDomain("news.163.com");
		t1.setUrlRegex("^http://news.163.com/\\d+/\\d+/\\d+/\\w+.html");
		t1.getElements().add(e1);
		t1.getElements().add(e2);

		Site s1 = new Site();
		s1.setName("网易新闻");
		s1.setUrl("http://news.163.com/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	@Test
	public void testTianya() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//h1[@id='hTitle']");
		e1.setType("Xpath");

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@class='post']");
		e2.setType("Xpath");

		Template t1 = new Template();
		t1.setDomain("www.tianya.cn");
		t1.setUrlRegex("^http://www.tianya.cn/\\w+/\\w+/\\w+/\\d+/\\d+.shtml");
		t1.getElements().add(e1);
		t1.getElements().add(e2);

		Site s1 = new Site();
		s1.setName("天涯论坛 ");
		s1.setUrl("http://www.tianya.cn/bbs/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	@Test
	public void testSinaBlog() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//div[@class='articalTitle']");
		e1.setType("Xpath");

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@class='articalContent']");
		e2.setType("Xpath");

		Template t1 = new Template();
		t1.setDomain("http://blog.sina.com.cn");
		t1.setUrlRegex("^http://blog.sina.com.cn/s/blog_\\S+.html\\?tj=1");
		t1.getElements().add(e1);
		t1.getElements().add(e2);

		Site s1 = new Site();
		s1.setName("新浪博客 ");
		s1.setUrl("http://blog.sina.com.cn/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}
}

package com.init;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dao.CommonDAO;
import com.model.policy.Element;
import com.model.policy.Element.ElementType;
import com.model.policy.Source;
import com.model.policy.Template;
import com.model.policy.Topic;
import com.util.SpringFactory;

public class InitDatabase {

	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	@BeforeClass
	public static void beforeClass() {
		/* clear sites */
		commonDAO.update("delete from Element");
		commonDAO.update("delete from Template");
		commonDAO.update("delete from Source");
		commonDAO.update("delete from Topic");
	}

	@Test
	public void topic() {
		Topic t1 = new Topic();
		t1.setName("test");
		t1.setInclude("我");
		commonDAO.save(t1);
	}

	@Test
	public void test163() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//h1[@id='h1title']");
		e1.setType(ElementType.XPATH);

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@id='endText']");
		e2.setType(ElementType.XPATH);

		Element e3 = new Element();
		e3.setName("pubTime");
		e3.setType(ElementType.XPATH);
		e3.setDefine("//div[@class='endContent']/span");

		Template t1 = new Template();
		t1.setDomain("news.163.com");
		t1.setUrlRegex("^http://news.163.com/\\d+/\\d+/\\d+/\\w+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);

		Source s1 = new Source();
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
		e1.setType(ElementType.XPATH);

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@class='post']");
		e2.setType(ElementType.XPATH);

		Element e3 = new Element();
		e3.setName("pubTime");
		e3.setType(ElementType.XPATH);
		e3.setDefine("body//div[4]//table//tbody//tr//td");

		Element e4 = new Element();
		e4.setName("replyNum");
		e4.setType(ElementType.XPATH);
		e4.setDefine("//div[@class='info']");
		e4.setRegex(".*回复：(\\d+)");

		Template t1 = new Template();
		t1.setDomain("www.tianya.cn");
		t1.setUrlRegex("^http://www.tianya.cn/\\w+/\\w+/\\w+/\\d+/\\d+.shtml");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setName("天涯论坛 ");
		s1.setUrl("http://www.tianya.cn/bbs/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	@Test
	public void testSinaBlog() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//h2[@class='titName SG_txta']");
		e1.setType(ElementType.XPATH);

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@class='articalContent  ']");
		e2.setType(ElementType.XPATH);

		Element e3 = new Element();
		e3.setName("pubTime");
		e3.setType(ElementType.XPATH);
		e3.setDefine("body//div[2]//div//div[2]//div[2]//div//div[2]//div//div//span[3]");

		Template t1 = new Template();
		t1.setDomain("blog.sina.com.cn");
		t1.setUrlRegex("^http://blog.sina.com.cn/s/blog_\\S+.html\\?tj=1");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);

		Source s1 = new Source();
		s1.setName("新浪博客 ");
		s1.setUrl("http://blog.sina.com.cn/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}
}

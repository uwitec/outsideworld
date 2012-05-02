package com.init;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dao.CommonDAO;
import com.model.policy.Element;
import com.model.policy.Element.ElementType;
import com.model.policy.Param;
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
		commonDAO.update("delete from Param");
	}

	@Test
	public void weibo() {
		Param p1 = new Param();
		p1.setName1("appkey");
		p1.setValue1("1222837781");
		p1.setType("sinaweibo");
		commonDAO.save(p1);

		Param p2 = new Param();
		p2.setName1("appkey");
		p2.setValue1("1222837781");
		p2.setType("sinaweibo");
		commonDAO.save(p2);

		Param p3 = new Param();
		p3.setName1("appkey");
		p3.setValue1("1222837781");
		p3.setType("sinaweibo");
		commonDAO.save(p3);

		Param p4 = new Param();
		p4.setName1("token");
		p4.setValue1("801106206");
		p4.setName2("secret");
		p4.setValue2("030f769fcb345443df7f92ec4e711e1f");
		p4.setType("tencentweibo");
		commonDAO.save(p4);

		Param p5 = new Param();
		p5.setName1("token");
		p5.setValue1("801106206");
		p5.setName2("secret");
		p5.setValue2("030f769fcb345443df7f92ec4e711e1f");
		p5.setType("tencentweibo");
		commonDAO.save(p5);

		Param p6 = new Param();
		p6.setName1("token");
		p6.setValue1("801106206");
		p6.setName2("secret");
		p6.setValue2("030f769fcb345443df7f92ec4e711e1f");
		p6.setType("tencentweibo");
		commonDAO.save(p6);

		Param p7 = new Param();
		p7.setName1("p1");
		p7.setValue1("fangxia722@sohu.com");
		p7.setName2("p2");
		p7.setValue2("fangxia722");
		p7.setName3("p3");
		p7.setValue3("ta9lM8nbMhHM8ZLQmsI8");
		p7.setName4("p4");
		p7.setValue4("yftEFtbWH2N#MVZoW!^CdEu8RC*S!N1x8P6FFKo5");
		p7.setType("sohuweibo");
		commonDAO.save(p7);

		Param p8 = new Param();
		p8.setName1("p1");
		p8.setValue1("fangxia722@sohu.com");
		p8.setName2("p2");
		p8.setValue2("fangxia722");
		p8.setName3("p3");
		p8.setValue3("ta9lM8nbMhHM8ZLQmsI8");
		p8.setName4("p4");
		p8.setValue4("yftEFtbWH2N#MVZoW!^CdEu8RC*S!N1x8P6FFKo5");
		p8.setType("sohuweibo");
		commonDAO.save(p8);

		Param p9 = new Param();
		p9.setName1("p1");
		p9.setValue1("fangxia722@sohu.com");
		p9.setName2("p2");
		p9.setValue2("fangxia722");
		p9.setName3("p3");
		p9.setValue3("ta9lM8nbMhHM8ZLQmsI8");
		p9.setName4("p4");
		p9.setValue4("yftEFtbWH2N#MVZoW!^CdEu8RC*S!N1x8P6FFKo5");
		p9.setType("sohuweibo");
		commonDAO.save(p9);
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

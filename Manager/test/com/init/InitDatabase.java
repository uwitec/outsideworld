package com.init;

import org.junit.Before;
import org.junit.Test;

import com.dao.CommonDAO;
import com.model.Element;
import com.model.Site;
import com.model.Template;
import com.util.SpringFactory;

public class InitDatabase {

	private CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	@Before
	public void setUp() {

	}

	@Test
	public void testDatabase() {
		/* clear sites */
		commonDAO.update("delete from Element");
		commonDAO.update("delete from Template");
		commonDAO.update("delete from Site");

		/* Initialize sites */
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//h1[@id='h1title']");
		e1.setType("Xpath");

		Template t1 = new Template();
		t1.setDomain("http://news.163.com/");
		t1.setUrlRegex("^http://news.163.com/\\d+/\\d+/\\d+/\\w+.html");
		t1.getElements().add(e1);

		Site s1 = new Site();
		s1.setName("网易新闻");
		s1.setUrl("http://news.163.com/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}
}

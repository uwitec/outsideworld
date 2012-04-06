package com.init;

import org.junit.Before;
import org.junit.Test;

import com.dao.CommonDAO;
import com.model.Site;
import com.util.SpringFactory;

public class InitDatabase {

	private CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	@Before
	public void setUp() {

	}

	@Test
	public void testDatabase() {
		/* clear sites */
		commonDAO.update("delete from Site");

		/* Initialize sites */
		Site s1 = new Site();
		s1.setName("网易新闻");
		s1.setUrl("http://news.163.com/");
		commonDAO.save(s1);
	}
}

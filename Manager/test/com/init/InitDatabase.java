package com.init;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.model.Site;

public class InitDatabase {

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	private static HibernateTemplate hibernateTemplate = applicationContext
			.getBean("hibernateTemplate", HibernateTemplate.class);

	@Before
	public void setUp() {

	}

	@Test
	public void testDatabase() {
		/* clear sites */
		hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				Transaction transaction = session.beginTransaction();
				Query query = session.createQuery("delete from Site");
				int count = query.executeUpdate();
				transaction.commit();
				session.close();
				return count;
			}
		});
		/* Initialize sites */
		Site s1 = new Site();
		s1.setName("网易新闻");
		s1.setUrl("http://news.163.com/");
		hibernateTemplate.save(s1);
	}
}

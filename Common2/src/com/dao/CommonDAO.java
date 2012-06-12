package com.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class CommonDAO {

	private static HibernateTemplate hibernateTemplate;

	public static HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public static void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		CommonDAO.hibernateTemplate = hibernateTemplate;
	}

	public void save(Object entity) {
		hibernateTemplate.save(entity);
	}

	public void saveAll(List<?> list) {
		for (Object object : list) {
			save(object);
		}
	}

	public void delete(Object entity) {
		hibernateTemplate.delete(entity);
	}

	public void update(Object entity) {
		hibernateTemplate.update(entity);
	}

	public <T> T get(Class<T> clazz, Serializable id) {
		return hibernateTemplate.get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(Class<T> clazz) {
		return hibernateTemplate.find("from " + clazz.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> query(final String sql) {
		return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session)
					throws HibernateException, SQLException {
				List<T> list = null;
				Transaction transaction = session.beginTransaction();
				Query query = session.createQuery(sql);
				list = query.list();
				transaction.commit();
				session.close();
				return list;
			}
		});
	}

	public int update(final String sql) {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				Transaction transaction = session.beginTransaction();
				Query query = session.createQuery(sql);
				int count = query.executeUpdate();
				transaction.commit();
				session.close();
				return count;
			}
		});
	}
}

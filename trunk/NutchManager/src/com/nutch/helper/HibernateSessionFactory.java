package com.nutch.helper;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class HibernateSessionFactory {

	private final static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private final static Logger LOG = LoggerFactory
			.getLogger(HibernateSessionFactory.class);
	private final static ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<Session>();
	private final static ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<Transaction>();

	private static Configuration configuration = new Configuration();
	private static SessionFactory sessionFactory;
	private static String configFile = CONFIG_FILE_LOCATION;

	static {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (HibernateException e) {
			LOG.error("buildHibernateSessionFactory");
			throw new HibernateException(e);
		}
	}

	private HibernateSessionFactory() {

	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void rebuildSessionFactory() {
		synchronized (sessionFactory) {
			try {
				configuration = new Configuration();
				configuration.configure(configFile);
				sessionFactory = configuration.buildSessionFactory();
			} catch (HibernateException e) {
				LOG.error("rebuildSessionFactory");
				throw new HibernateException(e);
			}
		}
	}

	public static void rebuildSessionFactory(String configFile) {
		synchronized (sessionFactory) {
			HibernateSessionFactory.configFile = configFile;
			try {
				configuration = new Configuration();
				configuration.configure(configFile);
				sessionFactory = configuration
						.buildSessionFactory(new ServiceRegistryBuilder()
								.buildServiceRegistry());
			} catch (HibernateException e) {
				LOG.error("rebuildSessionFactory");
				throw new HibernateException(e);
			}
		}
	}

	public static Session getSession() {
		Session session = (Session) sessionThreadLocal.get();
		try {
			if (session == null || !session.isOpen()) {
				session = sessionFactory.openSession();
				sessionThreadLocal.set(session);
			}
		} catch (HibernateException e) {
			LOG.error("openSession");
			throw new HibernateException(e);
		}
		return session;
	}

	public static void closeSession() {
		Session session = (Session) sessionThreadLocal.get();
		sessionThreadLocal.set(null);
		try {
			if (session != null && session.isOpen()) {
				session.close();
			}
		} catch (HibernateException e) {
			LOG.error("close");
			throw new HibernateException(e);
		}
	}

	public static void beginTransaction() {
		Transaction transaction = (Transaction) transactionThreadLocal.get();
		try {
			if (transaction == null) {
				transaction = getSession().beginTransaction();
				transactionThreadLocal.set(transaction);
			}
		} catch (HibernateException e) {
			LOG.error("beginTransaction");
			throw new HibernateException(e);
		}
	}

	public static void commitTransaction() {
		Transaction transaction = (Transaction) transactionThreadLocal.get();

		try {
			if (transaction != null && !transaction.wasCommitted()
					&& !transaction.wasRolledBack()) {
				transaction.commit();
			}
			transactionThreadLocal.set(null);
		} catch (HibernateException e) {
			LOG.error("commitTransaction");
			throw new HibernateException(e);
		}
	}

	public static void rollbackTransaction() {
		Transaction transaction = (Transaction) transactionThreadLocal.get();
		try {
			transactionThreadLocal.set(null);
			if (transaction != null && !transaction.wasCommitted()
					&& !transaction.wasRolledBack()) {
				transaction.rollback();
			}
		} catch (HibernateException e) {
			LOG.error("rollbackTransaction");
			throw new HibernateException(e);
		} finally {
			closeSession();
		}
	}

	public static void add(Object entity) {
		try {
			beginTransaction();
			getSession().save(entity);
			commitTransaction();
			rollbackTransaction();
		} catch (HibernateException e) {
			LOG.error("add");
			throw new HibernateException(e);
		}
	}

	public static void addList(List<?> list) {
		try {
			beginTransaction();
			for (Object entity : list) {
				getSession().save(entity);
			}
			commitTransaction();
			rollbackTransaction();
		} catch (HibernateException e) {
			LOG.error("add");
			throw new HibernateException(e);
		}
	}

	public static void update(Object entity) {
		try {
			beginTransaction();
			getSession().update(entity);
			commitTransaction();
			rollbackTransaction();
		} catch (HibernateException e) {
			LOG.error("update");
			throw new HibernateException(e);
		}
	}

	public static void delete(Object entity) {
		try {
			beginTransaction();
			getSession().delete(entity);
			commitTransaction();
			rollbackTransaction();
		} catch (HibernateException e) {
			LOG.error("delete");
			throw new HibernateException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz, Serializable id) {
		T entity = null;
		try {
			entity = (T) getSession().get(clazz, id);
		} catch (HibernateException e) {
			LOG.error("get");
			throw new HibernateException(e);
		} finally {
			closeSession();
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> query(String sql) {
		List<T> list = null;
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(sql);
		list = query.list();
		session.close();
		return list;
	}

	public static int update(String sql) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		transaction.commit();
		session.close();
		return count;
	}
}
package com.qq.group;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.CommonDAO;
import com.model.policy.QQInfo;

public class Main {

	private static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"qqContext.xml");

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanid) {
		return (T) appContext.getBean(beanid);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CommonDAO commonDAO = Main.getBean("commonDAO");
		List<QQInfo> qqs = commonDAO.getAll(QQInfo.class);
		for (QQInfo qq : qqs) {
			Login l = new Login();
			PollGroupMessage p = new PollGroupMessage(l.login(qq.getUserName(),
					qq.getPassword()), qq.getElements());
			p.start();
		}
	}
}

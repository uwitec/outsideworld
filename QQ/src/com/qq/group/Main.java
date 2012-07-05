package com.qq.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.CommonDAO;
import com.model.policy.QQInfo;
import com.model.policy.Source;

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
		// QQ
		CommonDAO commonDAO = Main.getBean("commonDAO");
		List<QQInfo> qqs = commonDAO.getAll(QQInfo.class);

		// QQ Group and SourceId
		Map<String, Integer> groups = new HashMap<String, Integer>();
		List<Source> list = commonDAO
				.query("from Source s where s.type = 'QQ'");
		for (Source source : list) {
			groups.put(source.getUrl(), source.getId());
		}

		// QQ Client
		for (QQInfo qq : qqs) {
			Login l = new Login();
			PollGroupMessage p = new PollGroupMessage(l.login(qq.getUserName(),
					qq.getPassword()), groups);
			p.start();
		}
	}
}

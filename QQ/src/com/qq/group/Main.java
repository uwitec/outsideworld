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
	
	private static CommonDAO commonDAO = Main.getBean("commonDAO");

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanid) {
		return (T) appContext.getBean(beanid);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		//
		//setup();
		// QQ		
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
					qq.getPassword()), groups,l);
			p.start();
		}
	}
	
	public static void setup() throws Exception{
		QQInfo qq = new QQInfo();
		qq.setUserName("38348450");
		qq.setPassword("zhdwangtravelsky");
		commonDAO.save(qq);
//		Source s = new Source();
//		s.setType(Source.SourceType.QQ);
//		s.setUrl("142636675");
//		s.setName("xxxx");
//		commonDAO.save(s);
	}
}

package com.qq.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.CommonDAO;
import com.model.policy.QQInfo;
import com.model.policy.Source;

public class Main {
	private static Logger LOG = Logger.getLogger(Main.class);
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
		LOG.info("abtained QQ and Group from database!");
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
			LOG.info("begin qq,userName:"+qq.getUserName()+",password:"+qq.getPassword());
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

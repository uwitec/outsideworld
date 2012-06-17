package test.extract;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.CommonDAO;
import com.extract.Extract;
import com.model.Item;
import com.model.policy.Template;
import com.nutch.manager.Setup;
import com.util.Fetcher;
import com.util.TemplateCache;

public class ExtractTest {

	private static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"nutchContext.xml");

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanid) {
		return (T) appContext.getBean(beanid);
	}

	private CommonDAO commonDAO = ExtractTest.getBean("commonDAO");
	private Fetcher fetcher = new Fetcher();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private Extract extract = ExtractTest.getBean("extractChain");

	@Before
	public void setUp() {
		Setup.main(null);
		List<Template> templates = commonDAO.getAll(Template.class);
		for (Template template : templates) {
			TemplateCache.addTemplate(template);
		}
	}

	@Test
	public void testExtract() throws Exception {
		Item item = new Item();
		// item.setUrl("http://news.163.com/12/0408/18/7UJBEMPK0001124J.html");
		item.setUrl("http://bbs1.people.com.cn/postDetail.do?boardId=2&treeView=1&view=2&id=119774374");
		item.setUrl("http://bbs1.people.com.cn/postDetail.do?boardId=71&view=1&id=119798777");
		// item.setUrl("http://blog.sina.com.cn/s/blog_613c0d86010126wf.html?tj=1");
		fetcher.fetch(item);
		extract.process(item);
		printItem(item);
	}

	private void printItem(Item item) {
		System.out.println("title:" + item.getTitle());
		System.out.println("content:" + item.getContent());
		System.out.println("urls number:" + item.getOurls().size());
		System.out.println("pub time:" + sdf.format(item.getPubTime()));
		System.out.println("reply num:" + item.getReplyNum());
	}
}

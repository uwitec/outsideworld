package test.extract;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dao.CommonDAO;
import com.dao.ItemDao;
import com.dao.mongo.ItemDaoImpl;
import com.extract.Extract;
import com.model.Item;
import com.model.policy.Template;
import com.util.Fetcher;
import com.util.SpringFactory;
import com.util.TemplateCache;

public class ExtractTest {

	private CommonDAO commonDAO = SpringFactory.getBean("commonDAO");
	private Fetcher fetcher = new Fetcher();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private Extract extract = SpringFactory.getBean("extractChain");

	@Before
	public void setUp() {
		List<Template> templates = commonDAO.getAll(Template.class);
		for (Template template : templates) {
			TemplateCache.addTemplate(template);
		}
	}

	@Test
	public void testExtract() throws Exception {
		Item item = new Item();
		// item.setUrl("http://news.163.com/12/0408/18/7UJBEMPK0001124J.html");
		item.setUrl("http://news.163.com/12/0604/01/8349K5SC00014JB6.html");
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

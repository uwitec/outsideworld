package test.extract;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dao.CommonDAO;
import com.dao.ItemDao;
import com.dao.mongo.ItemDaoImpl;
import com.extract.ExtractChain;
import com.model.Item;
import com.model.Template;
import com.util.SpringFactory;
import com.util.TemplateCache;

public class ExtractTest {

	private ExtractChain extractChain = SpringFactory.getBean("extractChain");
	private CommonDAO commonDAO = SpringFactory.getBean("commonDAO");
	private ItemDao itemDAO = new ItemDaoImpl();
	private Fetcher fetcher = new Fetcher();

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
		item.setUrl("http://news.163.com/12/0408/18/7UJBHEUU0001124J.html");
		fetcher.fetch(item);
		extractChain.process(item);
		printItem(item);
		itemDAO.insert(item);
	}

	private void printItem(Item item) {
		System.out.println("title:" + item.getTitle());
		System.out.println("content:" + item.getContent());
		System.out.println("urls number:" + item.getOurls().size());
	}
}

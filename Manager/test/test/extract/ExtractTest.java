package test.extract;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
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

	@Before
	public void setUp() {
		List<Template> templates = commonDAO.getAll(Template.class);
		for (Template template : templates) {
			TemplateCache.addTemplate(template);
		}
	}

	@Test
	public void testExtract() throws Exception {
		String content = FileUtils.readFileToString(new File("web/web1.txt"));
		Item item = new Item();
		item.setRawData(content.getBytes());
		item.setEncoding("UTF-8");
		item.setUrl("http://news.163.com/12/0407/14/7UGB0QJI00011229.html");
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

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
		// 1.强国经济论坛
		item.setUrl("http://bbs1.people.com.cn/postDetail.do?boardId=2&treeView=1&view=2&id=119774374");
		// 2.强国百姓监督
		item.setUrl("http://bbs1.people.com.cn/postDetail.do?boardId=71&view=1&id=119798777");
		// 3.天涯百姓声音
		item.setUrl("http://www.tianya.cn/techforum/content/828/1/196138.shtml");
		// 4.天涯实话实说
		item.setUrl("http://www.tianya.cn/techforum/content/972/1/25427.shtml");
		// 5.网易江苏论坛
		item.setUrl("http://bbs.local.163.com/bbs/localjs/252866772.html");
		// 6.网易房产论坛
		item.setUrl("http://bbs.gz.house.163.com/bbs/share/253012755.html");
		// 7.搜房保利西山林语业主论坛
		item.setUrl("http://bbs.soufun.com/1010187111~8~9586/165158771_165158771.htm");
		// 8.搜狐保利西山林语业主论坛
		item.setUrl("http://house.focus.cn/msgview/5158/233980246.html");
		// 9.长城论坛-名声热线
		item.setUrl("http://bbs.hebei.com.cn/thread-3985441-1-1.html");
		// 10.长城论坛-名声热线
		item.setUrl("http://club.kdnet.net/dispbbs.asp?page=1&boardid=1&id=8373738");
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

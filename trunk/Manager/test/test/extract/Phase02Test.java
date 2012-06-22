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
import com.model.policy.Element;
import com.model.policy.Source;
import com.model.policy.Source.SourceType;
import com.model.policy.Template;
import com.util.Fetcher;
import com.util.TemplateCache;

public class Phase02Test {

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

		clear();
		site01();
		site02();
		site03();
		site04();
		site05();
		site06();
		site07();
		site08();
		site09();
		site10();

		List<Template> templates = commonDAO.getAll(Template.class);
		for (Template template : templates) {
			TemplateCache.addTemplate(template);
		}
	}

	// 强国法制版
	public void site01() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//td[@class='box-content']/script");
		e2.setRegex("new TextParser\\('(\\S*)', '\\S*'\\);");
		e2.setFormat("javascript");
		Element e3 = new Element("pubTime",
				"//td[@class='box-title-treecontent']");
		Element e4 = new Element("replyNum",
				"//td[@class='box-title-treecontent2']");
		e4.setRegex("跟贴总数：(\\d+)");

		Template t1 = new Template();
		t1.setDomain("bbs1.people.com.cn");
		t1.setUrlRegex("^http://bbs1.people.com.cn/postDetail.do\\?boardId=9\\S*");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5001);
		s1.setName("人民网-强国社区-法制论坛 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs1.people.com.cn/boardList.do?action=postList&boardId=9");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site02() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//td[@class='box-content']/script");
		e2.setRegex("new TextParser\\('(.*)', '.*'\\);");
		e2.setFormat("javascript");
		Element e3 = new Element("pubTime", "//td[@class='box-time']");
		Element e4 = new Element("replyNum", "//td[@class='box-vote']");
		e4.setRegex(".*共 (\\d+) 个跟贴");

		Template t1 = new Template();
		t1.setDomain("bbs1.people.com.cn");
		t1.setUrlRegex("^http://bbs1.people.com.cn/postDetail.do\\?boardId=71.*");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5002);
		s1.setName("人民网-强国社区-百姓监督 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs1.people.com.cn/boardList.do?action=postList&boardId=71");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site03() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='post']");
		Element e3 = new Element("pubTime", "//div[@class='vcard']");
		Element e4 = new Element("replyNum", "//div[@class='info']");
		e4.setRegex(".*回复数：(\\d+)");

		Template t1 = new Template();
		t1.setDomain("www.tianya.cn");
		t1.setUrlRegex("^http://www.tianya.cn/techforum/content/828/\\d+/\\d+.shtml");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5003);
		s1.setName("天涯网-百姓声音 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://www.tianya.cn/techforum/articleslist/0/828.shtml");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site04() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='post']");
		Element e3 = new Element("pubTime", "//div[@class='vcard']");
		Element e4 = new Element("replyNum", "//div[@class='info']");
		e4.setRegex(".*回复数：(\\d+)");

		Template t1 = new Template();
		t1.setDomain("www.tianya.cn");
		t1.setUrlRegex("^http://www.tianya.cn/techforum/content/972/\\d+/\\d+.shtml");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5004);
		s1.setName("天涯网-实话实说 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://www.tianya.cn/techforum/articleslist/0/972.shtml");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site05() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='articleCont']");
		Element e3 = new Element("pubTime", "//span[@class='right']");
		Element e4 = new Element("replyNum", "//div[@class='summary']");
		e4.setRegex(".*浏览(\\d+)回帖");

		Template t1 = new Template();
		t1.setDomain("bbs.local.163.com");
		t1.setUrlRegex("^http://bbs.local.163.com/bbs/localjs/\\d+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5005);
		s1.setName("网易江苏论坛");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.local.163.com/list/localjs.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site06() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='articleCont']");
		Element e3 = new Element("pubTime", "//span[@class='right']");
		Element e4 = new Element("replyNum", "//div[@class='summary']");
		e4.setRegex(".*浏览(\\d+)回帖");

		Template t1 = new Template();
		t1.setDomain("bbs.gz.house.163.com");
		t1.setUrlRegex("^http://bbs.gz.house.163.com/bbs/\\w+/\\d+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5006);
		s1.setName("网易房产论坛");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.gz.house.163.com/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site07() {
		Element e1 = new Element("title", "//span[@class='d_t_t_01']");
		Element e2 = new Element("content", "//div[@id='zynr_comment']");
		Element e3 = new Element("pubTime", "//ul[@class='rtleft']");
		Element e4 = new Element("replyNum", "//div[@class='minb_right']");
		e4.setRegex(".*回复(\\d+)个");

		Template t1 = new Template();
		t1.setDomain("bbs.soufun.com");
		t1.setUrlRegex("^http://bbs.soufun.com/1010187111~.*.htm");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5007);
		s1.setName("搜房-保利西山林语业主论坛");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.soufun.com/board/1010187111/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site08() {
		Element e1 = new Element("title", "div[@class='title']/div[@class='l']");
		Element e2 = new Element("content", "//div[@class='text']");
		Element e3 = new Element("pubTime",
				"//div[@class='toptitle clear']//h4");
		Element e4 = new Element("replyNum",
				"//div[@class='title']/div[@class='r']");
		e4.setRegex(".*回复数 \\[(\\d+)\\]");

		Template t1 = new Template();
		t1.setDomain("house.focus.cn");
		t1.setUrlRegex("^http://house.focus.cn/msgview/\\d+/\\d+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5008);
		s1.setName("搜狐-保利西山林语业主论坛");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://house.focus.cn/msglist/5158/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site09() {
		Element e1 = new Element("title", "//a[@id='thread_subject']");
		Element e2 = new Element("content", "//td[@class='t_f']");
		Element e3 = new Element("pubTime", "//div[@class='authi']/em");
		Element e4 = new Element("replyNum", "//div[@class='hm']");
		e4.setRegex("回复: (\\d+)");

		Template t1 = new Template();
		t1.setDomain("bbs.hebei.com.cn");
		t1.setUrlRegex("^http://bbs.hebei.com.cn/thread-\\d+-\\d+-\\d+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5009);
		s1.setName("长城论坛-名声热线");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.hebei.com.cn/forum-722-1.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site10() {
		Element e1 = new Element("title", "//div[@class='posts-title']");
		Element e2 = new Element("content", "//div[@class='posts-cont']");
		Element e3 = new Element("pubTime", "//div[@class='posts-posted']");
		Element e4 = new Element("replyNum", "//div[@class='posts-stat-c']");
		e4.setRegex("(\\d+) 个回复");

		Template t1 = new Template();
		t1.setDomain("club.kdnet.net");
		t1.setUrlRegex("^http://club.kdnet.net/dispbbs.asp\\?page=\\d+&boardid=1&id=\\d+");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5010);
		s1.setName("凯迪社区-猫眼看人");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://club.kdnet.net/list.asp?boardid=1");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void clear() {
		/* clear sites */
		commonDAO.update("delete from Element");
		commonDAO.update("delete from Template");
		commonDAO.update("delete from Source");
		commonDAO.update("delete from Topic");
		commonDAO.update("delete from Param");
	}

	public static void test() {

	}

	@Test
	public void testExtract() throws Exception {
		Item item = new Item();
		// 1.强国法制坛
		item.setUrl("http://bbs1.people.com.cn/postDetail.do?boardId=9&treeView=1&view=2&id=119996168");
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

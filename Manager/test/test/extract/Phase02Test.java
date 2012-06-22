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
		site11();

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
		Element e5 = new Element("author", "//a[@class='link-username']");
		e5.setRegex("\\[(.*)\\]");

		Template t1 = new Template();
		t1.setDomain("bbs1.people.com.cn");
		t1.setUrlRegex("^http://bbs1.people.com.cn/postDetail.do\\?boardId=9\\S*");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5001);
		s1.setName("人民网-强国社区-法制论坛 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs1.people.com.cn/boardList.do?action=postList&boardId=9");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site02() {
		Element e1 = new Element("title", "//h1[@id='hTitle']");
		e1.setRegex("『\\S+』(\\S*)");
		Element e2 = new Element("content", "//div[@class='post']");
		Element e3 = new Element("pubTime", "//div[@class='vcard']");
		Element e4 = new Element("replyNum", "//div[@class='info']");
		e4.setRegex("回复数：(\\d+)");
		Element e5 = new Element("author", "//div[@class='vcard']");
		e5.setRegex("作者：(\\S+)");

		Template t1 = new Template();
		t1.setDomain("www.tianya.cn");
		t1.setUrlRegex("^http://www.tianya.cn/techforum/content/828/\\d+/\\d+.shtml");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5003);
		s1.setName("天涯网-百姓声音 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://www.tianya.cn/techforum/articleslist/0/828.shtml");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site03() {
		Element e1 = new Element("title", "//div[@class='posts-title']");
		e1.setRegex("\\[\\S*\\](\\S+)");
		Element e2 = new Element("content", "//div[@class='posts-cont']");
		Element e3 = new Element("pubTime", "//div[@class='posts-posted']");
		Element e4 = new Element("replyNum", "//div[@class='posts-stat-c']");
		e4.setRegex("(\\d+) 个回复");
		Element e5 = new Element("author", "//span[@class='c-main']");

		Template t1 = new Template();
		t1.setDomain("club.kdnet.net");
		t1.setUrlRegex("^http://club.kdnet.net/dispbbs.asp\\?page=\\d+&boardid=1&id=\\d+");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5010);
		s1.setName("凯迪社区-猫眼看人");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://club.kdnet.net/list.asp?boardid=1");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site04() {
		Element e1 = new Element("title", "//h1[@id='js-title']");
		Element e2 = new Element("content", "//div[@class='mainpart']");
		Element e3 = new Element("pubTime", "//div[@class='tzsm']");
		Element e4 = new Element("replyNum", "//div[@class='tzsm']");
		e4.setRegex("(\\d+)回复");
		Element e5 = new Element("author", "//div[@class='tzsm']");
		e5.setRegex("楼主：(\\S+)");

		Template t1 = new Template();
		t1.setDomain("tt.mop.com");
		t1.setUrlRegex("^http://tt.mop.com/read_\\S+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5004);
		s1.setName("猫扑社会广角 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://tt.mop.com/topic/list_70_19_0_0.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site05() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//p[@class='bbsp']");
		Element e3 = new Element("pubTime", "//div[@class='gray']");
		Element e4 = new Element("replyNum", "//div[@class='titr']");
		e4.setRegex("共(\\d+)个阅读者");
		Element e5 = new Element("author", "//td[@class='bbsname']");

		Template t1 = new Template();
		t1.setDomain("bbs.tiexue.net");
		t1.setUrlRegex("^http://bbs.tiexue.net/post_\\S+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5005);
		s1.setName("铁血社会聚焦");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.tiexue.net/default.htm?ListUrl=http://bbs.tiexue.net/bbs68-0-1.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site06() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='cont f14']");
		Element e3 = new Element("pubTime", "//div[@class='myInfo_up']");
		Element e4 = new Element("replyNum", "//div[@class='mybbs_cont']");
		e4.setRegex("回复(\\d+)次");
		Element e5 = new Element("author", "//div[@class='myInfo_up']/a");

		Template t1 = new Template();
		t1.setDomain("forum.book.sina.com.cn");
		t1.setUrlRegex("^http://forum.book.sina.com.cn/thread-\\S+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5006);
		s1.setName("新浪杂谈");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://forum.book.sina.com.cn/forum-51-1.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site07() {
		Element e1 = new Element("title", "//div[@class='mainsub']/h1");
		Element e2 = new Element("content", "//div[@class='cont f14']");
		Element e3 = new Element("pubTime", "//div[@class='myInfo_up']");
		Element e4 = new Element("replyNum", "//div[@class='mybbs_cont']");
		e4.setRegex("回复(\\d+)次");
		Element e5 = new Element("author", "//div[@class='myInfo_up']/a");

		Template t1 = new Template();
		t1.setDomain("club.life.sina.com.cn");
		t1.setUrlRegex("^http://club.life.sina.com.cn/health/thread-\\S+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5007);
		s1.setName("新浪杂谈暴光台");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://club.life.sina.com.cn/health/forum-45-1.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site08() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='articleCont']");
		Element e3 = new Element("pubTime", "//div[@class='right']");
		Element e4 = new Element("replyNum", "//div[@class='summary']");
		e4.setRegex("(\\d+)回帖");
		Element e5 = new Element("author", "//div[@class='name']");

		Template t1 = new Template();
		t1.setDomain("bbs.news.163.com");
		t1.setUrlRegex("^http://bbs.news.163.com/bbs/shishi/\\d+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5008);
		s1.setName("网易论坛");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.news.163.com/list/shishi.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site09() {
		Element e1 = new Element("title", "//a[@id='thread_subject']");
		Element e2 = new Element("content", "//td[@class='t_fsz']");
		Element e3 = new Element("pubTime", "//div[@class='authi']/em");
		Element e4 = new Element("replyNum", "//span[@class='vcnt']");
		e4.setRegex("回复\\[(\\d+)\\]");
		Element e5 = new Element("author", "//div[@class='authi']/a/strong");

		Template t1 = new Template();
		t1.setDomain("bbs.news.qq.com");
		t1.setUrlRegex("^http://bbs.news.qq.com/t-\\d+-\\d+.htm");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5009);
		s1.setName("腾讯实话实说");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.news.qq.com/qqnews/12");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site10() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@id='content']/script/src");
		e2.setFormat("javascript");
		Element e3 = new Element("pubTime", "//div[@class='txtright']");
		Element e4 = new Element("replyNum", "//div[@class='right']");
		e4.setRegex("回复(\\d+)");
		Element e5 = new Element("author", "//span[@class='txtblue']");

		Template t1 = new Template();
		t1.setDomain("club.news.sohu.com");
		t1.setUrlRegex("^http://club.news.sohu.com/r-\\S+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5009);
		s1.setName("搜狐实话实说");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://club.news.sohu.com/l-zz0081-0-0-0-0.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site11() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='article clearfix']");
		Element e3 = new Element("pubTime", "//ul[@class='right']");
		Element e4 = new Element("replyNum", "//ul[@class='right']");
		e4.setRegex("回复：(\\d+)");
		Element e5 = new Element("author", "//div[@class='member']");
		e5.setRegex("作者：(\\S+)");

		Template t1 = new Template();
		t1.setDomain("bbs.ifeng.com");
		t1.setUrlRegex("^http://bbs.ifeng.com/viewthread.php\\?tid=\\S+");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5009);
		s1.setName("凤凰杂谈");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.ifeng.com/forumdisplay.php?fid=144");
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

	@Test
	public void testExtract() throws Exception {
		Item item = new Item();
		// 1.强国法制坛
		item.setUrl("http://bbs1.people.com.cn/postDetail.do?boardId=9&treeView=1&view=2&id=119996168");
		// 2.天涯百姓声音
		item.setUrl("http://www.tianya.cn/techforum/content/828/1/291816.shtml");
		// 3.凯迪猫眼看人
		item.setUrl("http://club.kdnet.net/dispbbs.asp?page=1&boardid=1&id=7775722");
		// 4.猫扑社会广角
		item.setUrl("http://tt.mop.com/read_12256978_1_0.html");
		// 5.铁血社区
		item.setUrl("http://bbs.tiexue.net/post_5902922_1.html");
		// 6.新浪杂谈
		item.setUrl("http://forum.book.sina.com.cn/thread-5048917-1-1.html");
		// 7.新浪杂谈暴光台
		item.setUrl("http://club.life.sina.com.cn/health/thread-604463-1-1.html");
		// 8.网易论坛
		item.setUrl("http://bbs.news.163.com/bbs/shishi/253758493.html");
		// 9.腾讯实话实说
		item.setUrl("http://bbs.news.qq.com/t-1033589-1.htm");
		// 10.搜狐实话实说;author:javascript
		item.setUrl("http://club.news.sohu.com/r-zz0081-477740-0-0-900.html");
		// 11.凤凰杂谈
		item.setUrl("http://bbs.ifeng.com/viewthread.php?tid=13489792&extra=page%3D1");

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
		System.out.println("author:" + item.getAuthor());
	}
}

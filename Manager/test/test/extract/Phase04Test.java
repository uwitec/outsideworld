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

public class Phase04Test {

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
//		site03();
//		site04();
//		site05();
//		site06();
//		site07();
//		site08();
//		site09();
//		site10();
//		site11();

		List<Template> templates = commonDAO.getAll(Template.class);
		for (Template template : templates) {
			TemplateCache.addTemplate(template);
		}
	}

	// 天涯车友会
	public void site01() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='t_msgfont']");
		Element e3 = new Element("pubTime",
				"//*[@style='padding-top:4px;'][1].text()");
		Element e4 = new Element("replyNum",
				"//*[@class='header']/div[@class='right']/b[2]");
//		Element e5 = new Element("author",
//				"//*[@id='form1']/div/table/tbody/tr/td[2]/font/font");
//		e5.setRegex("作者：(\\S+)提交日期");

		Template t1 = new Template();
		t1.setDomain("bbs.voc.com.cn");
		t1.setUrlRegex("^http://bbs.voc.com.cn/topic*");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setId(5001);
		s1.setName("华生地方论坛");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.voc.com.cn/forum-132-1.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site02() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='pcb']");
		Element e3 = new Element("pubTime", "//div[@class='authi']/em");
		Element e4 = new Element("replyNum",
				"//div[@class='hm']/span[@class='xi1'][2]");
		//Element e5 = new Element("author", "//div[@id='lzxx_fun']/div/a");

		Template t1 = new Template();
		t1.setDomain("bbs.online.sh.cn");
		t1.setUrlRegex("^http://bbs.online.sh.cn/forum/thread*");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e4);
		t1.getElements().add(e3);

		Source s1 = new Source();
		s1.setId(5003);
		s1.setName("上海热线");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.online.sh.cn/forum");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site03() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='pcb']");
		Element e3 = new Element("pubTime", "//div[@class='authi']/em");
		Element e4 = new Element("replyNum", "//td[@class='pls']");
		e4.setRegex("回复: (\\d+)");
		Element e5 = new Element("author", "//div[@class='authi']");

		Template t1 = new Template();
		t1.setDomain("club.auto.qq.com");
		t1.setUrlRegex("^http://club.auto.qq.com/thread-\\S+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5010);
		s1.setName("腾讯侃车天地版");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://club.auto.qq.com/forum-16-1.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site04() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='a-content']");
		Element e3 = new Element("pubTime", "//div[@class='a-content']");
		Element e4 = new Element("replyNum", "//li[@class='page-pre']");
		e4.setRegex("贴数:(\\d+)");
		Element e5 = new Element("author", "//div[@class='a-u-uid']");

		Template t1 = new Template();
		t1.setDomain("www.newsmth.net");
		t1.setUrlRegex("^http://www.newsmth.net/nForum/#!article/RealEstate/\\d+");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5004);
		s1.setName("水木房地产 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://www.newsmth.net/nForum/board/RealEstate");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site05() {
		Element e1 = new Element("title", "//title");
		Element e2 = new Element("content", "//div[@class='articleCont']");
		Element e3 = new Element("pubTime", "//span[@class='right']");
		Element e4 = new Element("replyNum", "//div[@class='summary']");
		e4.setRegex(".*浏览(\\d+)回帖");
		Element e5 = new Element("author", "//div[@class='name']");

		Template t1 = new Template();
		t1.setDomain("bbs.local.163.com");
		t1.setUrlRegex("^http://bbs.local.163.com/bbs/localjs/\\d+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5005);
		s1.setName("网易江苏版本");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.local.163.com/list/localjs.html");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public void site06() {
		Element e1 = new Element("title", "//div[@id='threadtitle']");
		Element e2 = new Element("content", "//div[@class='t_msgfontfix']");
		Element e3 = new Element("pubTime",
				"//div[@class='authorinfo']/em/span/@title");
		// Element e4 = new Element("replyNum", "");
		Element e5 = new Element("author", "//a[@class='posterlink']");

		Template t1 = new Template();
		t1.setDomain("bbs.ynet.com");
		t1.setUrlRegex("^http://bbs.ynet.com/viewthread.php\\?tid=\\d+&\\S+");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		// t1.getElements().add(e4);
		t1.getElements().add(e5);

		Source s1 = new Source();
		s1.setId(5006);
		s1.setName("北青论坛");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://bbs.ynet.com/index.php");
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
		// 1.天涯车友会
		item.setUrl("http://bbs.voc.com.cn/topic-4222853-1-1.html");
		item.setUrl("http://bbs.online.sh.cn/forum/thread-3421228-1-1.html");
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

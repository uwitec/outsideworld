package com.nutch.manager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.CommonDAO;
import com.model.policy.Element;
import com.model.policy.Element.ElementType;
import com.model.policy.Param;
import com.model.policy.Source;
import com.model.policy.Source.SourceType;
import com.model.policy.Template;
import com.model.policy.Topic;
import com.weibo.client.SinaWeiboClient;
import com.weibo.client.SohuWeiboClient;
import com.weibo.client.TencentWeiboClient;

public class Setup {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"setupContext.xml");

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanid) {
		return (T) context.getBean(beanid);
	}

	private static CommonDAO commonDAO = Setup.getBean("commonDAO");

	public static void clear() {
		/* clear sites */
		commonDAO.update("delete from Element");
		commonDAO.update("delete from Template");
		commonDAO.update("delete from Source");
		commonDAO.update("delete from Topic");
		commonDAO.update("delete from Param");
	}

	public static void meta() {
		// 必应
		Source s1 = new Source();
		s1.setName("必应");
		s1.setType(SourceType.METASE);
		s1.setUrl("Bing");
		s1.setId(200001);
		commonDAO.save(s1);

		Param p1 = new Param();
		p1.setName1("name");
		p1.setValue1("必应");
		p1.setName2("URL");
		p1.setValue2("http://cn.bing.com/search?q=${KEYWORD}&first=${OFFSET}");
		p1.setName3("page");
		p1.setValue3("3");
		p1.setName4("title");
		p1.setValue4("//li[@class='sa_wr']//h3/a");
		p1.setName5("sourceId");
		p1.setValue5("200001");
		p1.setType("metasearch");
		commonDAO.save(p1);

		// 百度
		Source s2 = new Source();
		s2.setName("百度");
		s2.setType(SourceType.METASE);
		s2.setUrl("Baidu");
		s2.setId(200002);
		commonDAO.save(s2);

		Param p2 = new Param();
		p2.setName1("name");
		p2.setValue1("百度");
		p2.setName2("URL");
		p2.setValue2("http://www.baidu.com/s?wd=${KEYWORD}&pn=${OFFSET}");
		p2.setName3("page");
		p2.setValue3("3");
		p2.setName4("title");
		p2.setValue4("//h3[@class='t']/a");
		p2.setName5("sourceId");
		p2.setValue5("200002");
		p2.setType("metasearch");
		commonDAO.save(p2);

		// 谷歌
		Source s3 = new Source();
		s3.setName("谷歌");
		s3.setType(SourceType.METASE);
		s3.setUrl("Google");
		s3.setId(200003);
		commonDAO.save(s3);

		Param p3 = new Param();
		p3.setName1("name");
		p3.setValue1("谷歌");
		p3.setName2("URL");
		p3.setValue2("http://www.google.com/#q=${KEYWORD}&start=${OFFSET}");
		p3.setName3("page");
		p3.setValue3("3");
		p3.setName4("title");
		p3.setValue4("//h3[@class='r']/a");
		p3.setName5("sourceId");
		p3.setValue5("200003");
		p3.setType("metasearch");
		commonDAO.save(p3);

		// 搜狗
		Source s4 = new Source();
		s4.setName("搜狗");
		s4.setType(SourceType.METASE);
		s4.setUrl("Sougo");
		s4.setId(200004);
		commonDAO.save(s4);

		Param p4 = new Param();
		p4.setName1("name");
		p4.setValue1("搜狗");
		p4.setName2("URL");
		p4.setValue2("http://www.sogou.com/web?query=${KEYWORD}&page=${PAGE}");
		p4.setName3("page");
		p4.setValue3("3");
		p4.setName4("title");
		p4.setValue4("//h3[@class='pt']/a");
		p4.setName5("sourceId");
		p4.setValue5("200004");
		p4.setType("metasearch");
		commonDAO.save(p4);

		// 有道
		Source s5 = new Source();
		s5.setName("有道");
		s5.setType(SourceType.METASE);
		s5.setUrl("Youdao");
		s5.setId(200005);
		commonDAO.save(s5);

		Param p5 = new Param();
		p5.setName1("name");
		p5.setValue1("有道");
		p5.setName2("URL");
		p5.setValue2("http://www.youdao.com/search?q=${KEYWORD}&start=${OFFSET}");
		p5.setName3("page");
		p5.setValue3("3");
		p5.setName4("title");
		p5.setValue4("//div[@class='t1']/h3/a");
		p5.setName5("sourceId");
		p5.setValue5("200005");
		p5.setType("metasearch");
		commonDAO.save(p5);
	}

	public static void weibo() {
		Source s1 = new Source();
		s1.setName("新浪微博");
		s1.setType(Source.SourceType.WEIBO);
		s1.setUrl("SinaWeibo");
		s1.setId(SinaWeiboClient.typeId);
		commonDAO.save(s1);

		Source s2 = new Source();
		s2.setName("腾讯微博");
		s2.setType(Source.SourceType.WEIBO);
		s2.setUrl("TencentWeibo");
		s2.setId(TencentWeiboClient.typeId);
		commonDAO.save(s2);

		Source s3 = new Source();
		s3.setName("搜狐微博");
		s3.setType(Source.SourceType.WEIBO);
		s3.setUrl("SohuWeibo");
		s3.setId(SohuWeiboClient.typeId);
		commonDAO.save(s3);

		Param p1 = new Param();
		p1.setName1("appkey");
		p1.setValue1("1222837781");
		p1.setName2("interval");
		p1.setValue2("10");
		p1.setType("sinaweibo");
		commonDAO.save(p1);

		Param p2 = new Param();
		p2.setName1("appkey");
		p2.setValue1("1222837781");
		p2.setType("sinaweibo");
		p2.setName2("interval");
		p2.setValue2("10");
		commonDAO.save(p2);

		Param p3 = new Param();
		p3.setName1("appkey");
		p3.setValue1("1222837781");
		p3.setType("sinaweibo");
		p3.setName2("interval");
		p3.setValue2("10");
		commonDAO.save(p3);

		Param p4 = new Param();
		p4.setName1("customer_key");
		p4.setValue1("801106206");
		p4.setName2("customer_secret");
		p4.setValue2("030f769fcb345443df7f92ec4e711e1f");
		p4.setName3("token_key");
		p4.setValue3("21e08398f115494fb6291988c7b7027f");
		p4.setName4("token_secret");
		p4.setValue4("51ea6188ac86c8f2656d7d98bb051a6d");
		p4.setName5("interval");
		p4.setValue5("10");
		p4.setType("tencentweibo");
		commonDAO.save(p4);

		Param p5 = new Param();
		p5.setName1("customer_key");
		p5.setValue1("801106206");
		p5.setName2("customer_secret");
		p5.setValue2("030f769fcb345443df7f92ec4e711e1f");
		p5.setName3("token_key");
		p5.setValue3("21e08398f115494fb6291988c7b7027f");
		p5.setName4("token_secret");
		p5.setValue4("51ea6188ac86c8f2656d7d98bb051a6d");
		p5.setName5("interval");
		p5.setValue5("10");
		p5.setType("tencentweibo");
		commonDAO.save(p5);

		Param p6 = new Param();
		p6.setName1("customer_key");
		p6.setValue1("801106206");
		p6.setName2("customer_secret");
		p6.setValue2("030f769fcb345443df7f92ec4e711e1f");
		p6.setName3("token_key");
		p6.setValue3("21e08398f115494fb6291988c7b7027f");
		p6.setName4("token_secret");
		p6.setValue4("51ea6188ac86c8f2656d7d98bb051a6d");
		p6.setName5("interval");
		p6.setValue5("10");
		p6.setType("tencentweibo");
		commonDAO.save(p6);

		Param p7 = new Param();
		p7.setName1("p1");
		p7.setValue1("fangxia722@sohu.com");
		p7.setName2("p2");
		p7.setValue2("fangxia722");
		p7.setName3("p3");
		p7.setValue3("ta9lM8nbMhHM8ZLQmsI8");
		p7.setName4("p4");
		p7.setValue4("yftEFtbWH2N#MVZoW!^CdEu8RC*S!N1x8P6FFKo5");
		p7.setName5("interval");
		p7.setValue5("10");
		p7.setType("sohuweibo");
		commonDAO.save(p7);

		Param p8 = new Param();
		p8.setName1("p1");
		p8.setValue1("fangxia722@sohu.com");
		p8.setName2("p2");
		p8.setValue2("fangxia722");
		p8.setName3("p3");
		p8.setValue3("ta9lM8nbMhHM8ZLQmsI8");
		p8.setName4("p4");
		p8.setValue4("yftEFtbWH2N#MVZoW!^CdEu8RC*S!N1x8P6FFKo5");
		p8.setName5("interval");
		p8.setValue5("10");
		p8.setType("sohuweibo");
		commonDAO.save(p8);

		Param p9 = new Param();
		p9.setName1("p1");
		p9.setValue1("fangxia722@sohu.com");
		p9.setName2("p2");
		p9.setValue2("fangxia722");
		p9.setName3("p3");
		p9.setValue3("ta9lM8nbMhHM8ZLQmsI8");
		p9.setName4("p4");
		p9.setValue4("yftEFtbWH2N#MVZoW!^CdEu8RC*S!N1x8P6FFKo5");
		p9.setName5("interval");
		p9.setValue5("10");
		p9.setType("sohuweibo");
		commonDAO.save(p9);
	}

	public static void topic() {
		Topic t1 = new Topic();
		t1.setName("test");
		t1.setInclude("中国");
		commonDAO.save(t1);

		Topic t2 = new Topic();
		t2.setName("test1");
		t2.setInclude("吃");
		commonDAO.save(t2);

		Topic t3 = new Topic();
		t3.setName("test2");
		t3.setInclude("神经");
		commonDAO.save(t3);

		Topic t4 = new Topic();
		t4.setName("test3");
		t4.setInclude("手机");
		commonDAO.save(t4);

		Topic t5 = new Topic();
		t5.setName("test4");
		t5.setInclude("姐姐");
		commonDAO.save(t5);

		Topic t6 = new Topic();
		t6.setName("test5");
		t6.setInclude("母亲");
		commonDAO.save(t6);

		Topic t7 = new Topic();
		t7.setName("test6");
		t7.setInclude("大学");
		commonDAO.save(t7);
	}

	public static void test163() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//h1[@id='h1title']");
		e1.setType(ElementType.XPATH);

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@id='endText']");
		e2.setType(ElementType.XPATH);

		Element e3 = new Element();
		e3.setName("pubTime");
		e3.setType(ElementType.XPATH);
		e3.setDefine("//div[@class='endContent']/span");

		Template t1 = new Template();
		t1.setDomain("news.163.com");
		t1.setUrlRegex("^http://news.163.com/\\d+/\\d+/\\d+/\\w+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);

		Source s1 = new Source();
		s1.setName("网易新闻");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://news.163.com/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public static void testPicture() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("/div[7]/div/div[2]/div/h1/a");
		e1.setType(ElementType.XPATH);

		Template t1 = new Template();
		t1.setDomain("hd.penshow.cn");
		t1.setUrlRegex("^http://hd.penshow.cn/\\d+/\\d+/\\d+/\\w+.html");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);

		Source s1 = new Source();
		s1.setName("图片素材");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://hd.penshow.cn/");
		s1.getTempaltes().add(t1);
		commonDAO.save(s1);
	}

	public static void testTianya() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//h1[@id='hTitle']");
		e1.setType(ElementType.XPATH);

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@class='post']");
		e2.setType(ElementType.XPATH);

		Element e3 = new Element();
		e3.setName("pubTime");
		e3.setType(ElementType.XPATH);
		e3.setDefine("//div[@class='vcard']");

		Element e4 = new Element();
		e4.setName("replyNum");
		e4.setType(ElementType.XPATH);
		e4.setDefine("//div[@class='info']");
		e4.setRegex(".*回复.{0,1}：(\\d+)");

		Template t1 = new Template();
		t1.setDomain("www.tianya.cn");
		t1.setUrlRegex("^http://www.tianya.cn/\\w+/\\w+/\\w+/\\d+/\\d+.shtml");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);
		t1.getElements().add(e4);

		Source s1 = new Source();
		s1.setName("天涯论坛 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://www.tianya.cn/bbs/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public static void testSinaBlog() {
		Element e1 = new Element();
		e1.setName("title");
		e1.setDefine("//h2[@class='titName SG_txta']");
		e1.setType(ElementType.XPATH);

		Element e2 = new Element();
		e2.setName("content");
		e2.setDefine("//div[@class='articalContent  ']");
		e2.setType(ElementType.XPATH);

		Element e3 = new Element();
		e3.setName("pubTime");
		e3.setType(ElementType.XPATH);
		e3.setDefine("body//div[2]//div//div[2]//div[2]//div//div[2]//div//div//span[3]");

		Template t1 = new Template();
		t1.setDomain("blog.sina.com.cn");
		t1.setUrlRegex("^http://blog.sina.com.cn/s/blog_\\S+.html\\?tj=1");
		t1.setFetchInterval(1000 * 60);
		t1.getElements().add(e1);
		t1.getElements().add(e2);
		t1.getElements().add(e3);

		Source s1 = new Source();
		s1.setName("新浪博客 ");
		s1.setType(SourceType.WEBSITE);
		s1.setUrl("http://blog.sina.com.cn/");
		s1.getTempaltes().add(t1);

		commonDAO.save(s1);
	}

	public static void indexDir() {
		Param p1 = new Param();
		p1.setName1("index_dir");
		p1.setType("index_dir");
		p1.setValue1("/data/index");
		commonDAO.save(p1);
	}

	public static void cacheFile() {
		Param p1 = new Param();
		p1.setName1("corpus_file");
		p1.setType("corpus_file");
		p1.setValue1("/data/SogouLabDic.dic");
		commonDAO.save(p1);
	}

	public static void main(String[] args) {
		clear();
		meta();
		topic();
		weibo();
		test163();
		testSinaBlog();
		testTianya();
		indexDir();
		cacheFile();
	}

}

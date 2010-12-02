package com.aries.study.bookspider.qq;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aries.htmlmodifier.dom.INode;
import com.aries.htmlmodifier.dom.ITagNode;
import com.aries.htmlmodifier.exception.HtmlParseException;
import com.aries.study.bookspider.DataCenter;
import com.aries.study.bookspider.Formater;
import com.aries.study.bookspider.ObjectResultSetHandler;
import com.aries.study.bookspider.WebContent;

public class QQBook extends WebContent {

	private static final Logger LOG = LoggerFactory.getLogger(QQBook.class);

	/**
	 * @param myCategoryId
	 *            要存入当前数据库的哪个分类下
	 * @param url
	 *            图书起始页URL，如：
	 *            http://bookapp.book.qq.com/origin/workintro/376/work_2271608
	 *            .shtml
	 *            http://bookapp.book.qq.com/origin/workintro/477/work_2256349
	 *            .shtml
	 * @throws HtmlParseException
	 */
	public QQBook(int myCategoryId, String url) throws HtmlParseException {
		super(url);

		if (!super.isValid()) {
			LOG.error("Can not get content from {}", url);
			return;
		}

		LOG.info("从 {} 向分类 {} 添加图书", url, myCategoryId);

		// 图书作者
		String author = super.getFirstNode(QQBookConfig.author).innerHtml();
		author = Formater.formateTxt(author);

		int authorId = -1;
		QueryRunner queryRunner = new QueryRunner(DataCenter.getDataSource());
		try {
			ObjectResultSetHandler objRs = new ObjectResultSetHandler();
			List<Object[]> list = queryRunner.query(
					"select id from t_book_author where name=?", objRs, author);
			if (list == null || list.size() == 0) {
				LOG.info("添加作者：{}", author);
				queryRunner.update("insert into t_book_author set name=?",
						author);
				objRs = new ObjectResultSetHandler();
				authorId = Integer.parseInt(queryRunner.query(
						"select id from t_book_author where name=?", objRs,
						author).get(0)[0].toString());
			} else {
				authorId = Integer.parseInt(queryRunner.query(
						"select id from t_book_author where name=?", objRs,
						author).get(0)[0].toString());
				LOG.info("设定作者：{}", author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// 图书名称
		String bookName = super.getFirstNode(QQBookConfig.title).innerHtml();
		bookName = Formater.formateTxt(bookName);

		// 图书简介
		String bookAbstract = super.getFirstNode(QQBookConfig.abs).innerHtml();
		bookAbstract = Formater.formateTxt(bookAbstract);

		// TODO 验证是否存在

		// 插入数据库,并且取得当前图书的Id
		LOG.info("添加图书：{}", bookName);
		int newBookId = -1;
		queryRunner = new QueryRunner(DataCenter.getDataSource());
		try {
			queryRunner
					.update(
							"insert into t_book_info set category_id=?, author_id=?, url=?, name=?,abstract=?,update_time=now()",
							myCategoryId, authorId, url, bookName, bookAbstract);
			ObjectResultSetHandler objRs = new ObjectResultSetHandler();
			newBookId = Integer.parseInt(queryRunner.query(
					"select id from t_book_info where url=? and name=?", objRs,
					url, bookName).get(0)[0].toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 取得章节列表
		List<ITagNode> chapterList = super.getNodeList(QQBookConfig.chapter);
		for (ITagNode tagNode : chapterList) {
			ITagNode aTag = null;
			for (INode node : tagNode.getNodeList()) {
				if (node instanceof ITagNode) {
					aTag = (ITagNode) node;
					if (aTag.getTagName().equals("a")) {
						break;
					}
				}
			}

			// 章节名称
			String chapterName = aTag.innerHtml();
			chapterName = Formater.formateTxt(chapterName);

			// 章节ID
			String href = aTag.getAttr("href");
			Matcher matcher = QQBookConfig.contentLink.matcher(href);
			matcher.find();
			String workId = matcher.group(1);
			String chapterId = matcher.group(2);

			// 从Url中取得图书分类ID
			Matcher cateMatcher = QQBookConfig.category.matcher(url);
			cateMatcher.find();
			String category = cateMatcher.group(1);

			// 章节页面链接
			String contentHref = category + "/" + workId + "/chp_info_"
					+ chapterId + ".htm";

			// 章节页面内容
			WebContent chapterContent = new WebContent(contentHref);
			if (!chapterContent.isValid()) {
				continue;
			}

			ITagNode contentTag = chapterContent
					.getFirstNode(QQBookConfig.content);
			if (contentTag == null) {
				System.out.println("VIP Chapter and Exit");
				break;
			} else {
				String html = contentTag.outerHtml();
				String txt = contentTag.innerHtml();
				txt = Formater.formateTxt(txt);

				// 图书分类ID/图书ID
				String htmlPath = "/" + myCategoryId + "/" + newBookId + "/"
						+ chapterId + ".html";
				String txtPath = "/" + myCategoryId + "/" + newBookId + "/"
						+ chapterId + ".txt";

				try {
					FileUtils.writeStringToFile(new File(DataCenter
							.getStorePath(), htmlPath), html);
					FileUtils.writeStringToFile(new File(DataCenter
							.getStorePath(), txtPath), txt);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				queryRunner = new QueryRunner(DataCenter.getDataSource());
				try {
					LOG.info("向图书：{} 添加章节：{}", bookName, chapterName);
					queryRunner
							.update(
									"insert into t_book_chapter set book_id=?, name=?, url=?, txt=?,html=?,update_time=now()",
									newBookId, chapterName, contentHref,
									txtPath, htmlPath);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

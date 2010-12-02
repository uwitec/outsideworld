package com.aries.study.bookspider.qq;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;

import com.aries.htmlmodifier.dom.INode;
import com.aries.htmlmodifier.dom.ITagNode;
import com.aries.htmlmodifier.exception.HtmlParseException;
import com.aries.study.bookspider.DataCenter;
import com.aries.study.bookspider.ObjectResultSetHandler;
import com.aries.study.bookspider.WebContent;

public class QQBook extends WebContent {

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

		// 图书名称
		String bookName = super.getFirstNode(QQBookConfig.title).innerHtml();
		// 图书简介
		String bookAbstract = super.getFirstNode(QQBookConfig.abs).innerHtml();

		// TODO 验证是否存在

		// 插入数据库,并且取得当前图书的Id
		int newBookId = -1;
		QueryRunner queryRunner = new QueryRunner(DataCenter.getDataSource());
		try {
			queryRunner
					.update(
							"insert into t_book_info set category_id=1, author_id=1, url=?, name=?,abstract=?,update_time=now()",
							url, bookName, bookAbstract);
			ObjectResultSetHandler objRs = new ObjectResultSetHandler();
			newBookId = Integer.parseInt(queryRunner.query(
					"select id from t_book_info where url=?, name=?", objRs,
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
			ITagNode contentTag = chapterContent
					.getFirstNode(QQBookConfig.content);
			if (contentTag == null) {
				System.out.println("VIP Chapter and Exit");
				break;
			} else {
				String html = contentTag.outerHtml();
				String txt = contentTag.innerHtml();

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
					queryRunner
							.update(
									"insert into t_book_chapter set book_id=1, name=?, url=?, txt=?,html=?,update_time=now()",
									chapterName, contentHref, txtPath, htmlPath);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

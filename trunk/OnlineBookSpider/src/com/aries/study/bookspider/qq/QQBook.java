package com.aries.study.bookspider.qq;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;

import com.aries.htmlmodifier.dom.INode;
import com.aries.htmlmodifier.dom.ITagNode;
import com.aries.htmlmodifier.exception.HtmlParseException;
import com.aries.study.bookspider.DataCenter;
import com.aries.study.bookspider.WebContent;

public class QQBook extends WebContent {

	public QQBook(String url) throws HtmlParseException {
		super(url);

		Matcher cateMatcher = QQBookConfig.category.matcher(url);
		cateMatcher.find();
		String category = cateMatcher.group(1);
		System.out.println(category);

		String bookName = super.getFirstNode(QQBookConfig.title).innerHtml();
		String bookAbstract = super.getFirstNode(QQBookConfig.abs).innerHtml();
		QueryRunner queryRunner = new QueryRunner(DataCenter.getDataSource());
		try {
			queryRunner
					.update(
							"insert into t_book_info set category_id=1, author_id=1, url=?, name=?,abstract=?,update_time=now()",
							url, bookName, bookAbstract);
		} catch (SQLException e) {
			e.printStackTrace();
		}

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

			String chapterName = aTag.innerHtml();

			String href = aTag.getAttr("href");
			Matcher matcher = QQBookConfig.contentLink.matcher(href);
			matcher.find();
			String workId = matcher.group(1);
			String chapterId = matcher.group(2);
			String contentHref = category + "/" + workId + "/chp_info_"
					+ chapterId + ".htm";

			WebContent text = new WebContent(contentHref);
			ITagNode contentTag = text.getFirstNode(QQBookConfig.content);
			if (contentTag == null) {
				System.out.println("VIP Chapter and Exit");
				break;
			} else {
				String html = contentTag.outerHtml();
				String txt = contentTag.innerHtml();

				// ∑÷¿‡ID/BookId
				String htmlPath = "/1/1/" + chapterId + ".html";
				String txtPath = "/1/1/" + chapterId + ".txt";

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

	public void persistBook(DataSource dataSource) {

	}
}

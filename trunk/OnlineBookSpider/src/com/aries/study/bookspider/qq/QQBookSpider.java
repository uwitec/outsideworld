package com.aries.study.bookspider.qq;

import java.util.List;

import javax.sql.DataSource;

import com.aries.htmlmodifier.dom.ITagNode;
import com.aries.htmlmodifier.exception.HtmlParseException;
import com.aries.study.bookspider.WebContent;
import com.aries.util.db.SimpleDataBase;

public class QQBookSpider {
	private DataSource dataSource;

	public QQBookSpider() {
		String url = "jdbc:postgresql://localhost:3306/bookreader?useUnicode=true;characterEncoding=utf8";
		String driverClass = "com.mysql.jdbc.Driver";
		String user = "root";
		String password = "root";
		dataSource = SimpleDataBase.setupDataSource(url, driverClass, user,
				password);
	}

	public void execute() {
		try {
			WebContent bookList = new WebContent(
					"http://bookapp.book.qq.com/book_list/6_0_1_0.htm");
			System.out.println("asdf");
			List<ITagNode> bookNodeList = bookList
					.getNodeList(QQBookConfig.bookList);
			for (ITagNode node : bookNodeList) {
				String href = node.getAttr("href");
				System.out.println(href);
			}
		} catch (HtmlParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new QQBookSpider().execute();
	}
}

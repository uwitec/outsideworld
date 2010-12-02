package com.aries.study.bookspider.qq;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aries.htmlmodifier.dom.ITagNode;
import com.aries.htmlmodifier.exception.HtmlParseException;
import com.aries.study.bookspider.WebContent;

public class QQBookSpider {

	private static final Logger LOG = LoggerFactory
			.getLogger(QQBookSpider.class);

	public QQBookSpider() {

	}

	/**
	 * @param myCategoryId
	 * @param bookListUrl
	 *            图书列表页面 URL，如：
	 *            http://bookapp.book.qq.com/book_list/1_0_1_0.htm，
	 *            http://bookapp.book.qq.com/book_list/6_0_1_0.htm
	 */
	public void spideFromBookListPage(String bookListUrl, int myCategoryId) {
		LOG.info("将页面{}的图书放入{}分类", bookListUrl, myCategoryId);
		try {
			// 图书List
			WebContent bookList = new WebContent(bookListUrl);
			if (!bookList.isValid()) {
				System.exit(1);
			}
			List<ITagNode> bookNodeList = bookList
					.getNodeList(QQBookConfig.bookList);

			// 遍历图书List
			for (ITagNode node : bookNodeList) {
				// 图书链接
				String href = node.getAttr("href");
				String url = bookList.getHref(href);

				// 重定向页面（重定向到真正的图书页面）
				WebContent redirectPage = new WebContent(url);
				if (redirectPage.isValid()) {
					// 真正的图书链接
					String realUrl = redirectPage.getMatcher(
							QQBookConfig.bookDis).group(1);
					Thread t = new Thread(new QQBook(myCategoryId, realUrl));
					t.start();
				}
			}
		} catch (HtmlParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new QQBookSpider().spideFromBookListPage(
				"http://bookapp.book.qq.com/book_list/6_0_1_0.htm", 2);
	}
}

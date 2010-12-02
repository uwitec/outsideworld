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
	 *            ͼ���б�ҳ�� URL���磺
	 *            http://bookapp.book.qq.com/book_list/1_0_1_0.htm��
	 *            http://bookapp.book.qq.com/book_list/6_0_1_0.htm
	 */
	public void spideFromBookListPage(String bookListUrl, int myCategoryId) {
		LOG.info("��ҳ��{}��ͼ�����{}����", bookListUrl, myCategoryId);
		try {
			// ͼ��List
			WebContent bookList = new WebContent(bookListUrl);
			if (!bookList.isValid()) {
				System.exit(1);
			}
			List<ITagNode> bookNodeList = bookList
					.getNodeList(QQBookConfig.bookList);

			// ����ͼ��List
			for (ITagNode node : bookNodeList) {
				// ͼ������
				String href = node.getAttr("href");
				String url = bookList.getHref(href);

				// �ض���ҳ�棨�ض���������ͼ��ҳ�棩
				WebContent redirectPage = new WebContent(url);
				if (redirectPage.isValid()) {
					// ������ͼ������
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

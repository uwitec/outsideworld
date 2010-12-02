package com.aries.study.bookspider.qq;

import java.util.List;

import com.aries.htmlmodifier.dom.ITagNode;
import com.aries.htmlmodifier.exception.HtmlParseException;
import com.aries.study.bookspider.WebContent;

public class QQBookSpider {

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
		try {
			// ͼ��List
			WebContent bookList = new WebContent(bookListUrl);
			List<ITagNode> bookNodeList = bookList
					.getNodeList(QQBookConfig.bookList);

			// ����ͼ��List
			for (ITagNode node : bookNodeList) {
				// ͼ������
				String href = node.getAttr("href");
				String url = bookList.getHref(href);

				// �ض���ҳ�棨�ض���������ͼ��ҳ�棩
				WebContent redirectPage = new WebContent(url);

				// ������ͼ������
				String realUrl = redirectPage.getMatcher(QQBookConfig.bookDis)
						.group(1);
				new QQBook(myCategoryId, realUrl);
			}
		} catch (HtmlParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new QQBookSpider().spideFromBookListPage(
				"http://bookapp.book.qq.com/book_list/1_0_1_0.htm", 1);
	}
}

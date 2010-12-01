package com.aries.study.bookspider.qq;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.aries.htmlmodifier.dom.INode;
import com.aries.htmlmodifier.dom.ITagNode;
import com.aries.htmlmodifier.exception.HtmlParseException;
import com.aries.study.bookspider.WebContent;

public class QQBook extends WebContent {

	private String title;

	private List<String> chapter = new ArrayList<String>(10);

	private List<String> content = new ArrayList<String>(10);

	public QQBook(String url) throws HtmlParseException {
		super(url);

		Matcher cateMatcher = QQBookConfig.category.matcher(url);
		cateMatcher.find();
		String category = cateMatcher.group(1);
		System.out.println(category);

		title = super.getFirstNode(QQBookConfig.title).innerHtml();
		System.out.println(title);

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
			chapter.add(aTag.innerHtml());

			String href = aTag.getAttr("href");
			Matcher matcher = QQBookConfig.contentLink.matcher(href);
			matcher.find();
			String workId = matcher.group(1);
			String chapterId = matcher.group(2);
			String contentHref = category + "/" + workId + "/chp_info_"
					+ chapterId + ".htm";
			System.out.println(aTag.innerHtml());

			WebContent text = new WebContent(contentHref);
			ITagNode contentTag = text.getFirstNode(QQBookConfig.content);
			if (contentTag == null) {
				System.out.println("VIP Chapter and Exit");
				break;
			} else {
				String html = contentTag.outerHtml();
				content.add(html);
				System.out.println(html);
			}
		}
	}
}

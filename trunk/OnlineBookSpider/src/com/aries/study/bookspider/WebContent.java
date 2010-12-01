package com.aries.study.bookspider;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aries.htmlmodifier.HtmlParser;
import com.aries.htmlmodifier.INodeFilter;
import com.aries.htmlmodifier.dom.ITagNode;
import com.aries.htmlmodifier.exception.HtmlParseException;
import com.aries.util.http.HttpToolkit;

public class WebContent {
	private static final Logger LOG = LoggerFactory.getLogger(WebContent.class);

	private String url;

	private String content;

	private HtmlParser parser;

	public WebContent(String url) throws HtmlParseException {
		this.url = url;
		HttpToolkit httpToolkiet = new HttpToolkit();
		LOG.info("Get content from {}", url);
		content = httpToolkiet.get(url);
		LOG.info("Parse HTML content of {}", url);
		parser = new HtmlParser();
		parser.parse(content);
	}

	public String getHref(String href) {
		if (href.startsWith("http://")) {
			return href;
		} else {
			int index = url.replace("http://", "").indexOf('/');
			return url.substring(0, index + "http://".length()) + href;
		}
	}

	public ITagNode getFirstNode(INodeFilter nodeFilter) {
		List<ITagNode> tagList = parser.getNodes(nodeFilter);
		if (tagList == null || tagList.size() < 1) {
			return null;
		} else {
			return tagList.get(0);
		}
	}

	public List<ITagNode> getNodeList(INodeFilter nodeFilter) {
		return parser.getNodes(nodeFilter);
	}
}

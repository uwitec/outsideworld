package com.spider.extract;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.entity.Template;
import com.model.Page;
import com.spider.filter.DuplicatedUrlFilter;
import com.spider.filter.IntervalFilter;

public class UrlExtractor implements Extractor {

	private HtmlCleaner htmlCleaner;
	private DuplicatedUrlFilter duplicatedUrlFilter;
	private IntervalFilter intervalFilter;
	private ConcurrentLinkedQueue<Page> fetchQueue;
	private ConcurrentLinkedQueue<Page> extractQueue;

	@Override
	public int extract(Page page, Set<Template> templates) throws Exception {
		// 如果page的domain和source的domain不是一个domain，则不进行抽取
		if (!StringUtils.contains(page.getDomain(), page.getSource()
				.getDomain())) {
			System.out.println("domain不匹配，页面domain为" + page.getDomain()
					+ ",source的domain为" + page.getSource().getDomain());
			return -1;
		}
		// 如果depth深度超过，则不进行解析
		if (page.getDepth() >= page.getSource().getDepth()) {
			return -1;
		}
		if (StringUtils.equals(page.getUrl().toString(),
				"http://www.penshow.cn/tags/")) {
			System.out.println("OK");
		}
		TagNode root;
		if (page.getDoc() == null) {
			if (!StringUtils.isBlank(page.getHtml())) {
				root = htmlCleaner.clean(page.getHtml());
				page.setDoc(root);
				Object[] hrefs = root.evaluateXPath("//a/@href");
				// 1、如果domain匹配的话，则进行提取，否则不进行提取
				List<String> links = new ArrayList<String>();
				for (Object href : hrefs) {
					String link = href.toString();
					if (StringUtils.startsWith(link, "http")) {
						links.add(link);
					}
				}
				// 2、这里将urls分类，如果是目录页，则使用interval
				// Filter，查看是否过期，如果是抽取页面，则使用bloom过滤器进行
				for (String link : links) {
					Page item = new Page();
					item.setUrl(new URL(link));
					item.setSource(page.getSource());
					item.setDepth(page.getDepth() + 1);
					item.setDomain(new URL(link).getHost());
					for (Template template : templates) {
						if (template.match(link)) {
							System.out.println("发现抽取页面:" + link);
							item.setType(1);
							// 结果页面
							if (duplicatedUrlFilter.filter(item)) {
								extractQueue.add(page);
								break;
							}
						}

					}
					// 目录页面，查看时间
					if (item.getType() == 0 && intervalFilter.filter(item)) {
						System.out.println("发现目录页面:" + link);
						fetchQueue.add(item);
					}
				}
			}
		}
		return -1;
	}

	public HtmlCleaner getHtmlCleaner() {
		return htmlCleaner;
	}

	public void setHtmlCleaner(HtmlCleaner htmlCleaner) {
		this.htmlCleaner = htmlCleaner;
	}

	public DuplicatedUrlFilter getDuplicatedUrlFilter() {
		return duplicatedUrlFilter;
	}

	public void setDuplicatedUrlFilter(DuplicatedUrlFilter duplicatedUrlFilter) {
		this.duplicatedUrlFilter = duplicatedUrlFilter;
	}

	public IntervalFilter getIntervalFilter() {
		return intervalFilter;
	}

	public void setIntervalFilter(IntervalFilter intervalFilter) {
		this.intervalFilter = intervalFilter;
	}

	public ConcurrentLinkedQueue<Page> getFetchQueue() {
		return fetchQueue;
	}

	public void setFetchQueue(ConcurrentLinkedQueue<Page> fetchQueue) {
		this.fetchQueue = fetchQueue;
	}

	public ConcurrentLinkedQueue<Page> getExtractQueue() {
		return extractQueue;
	}

	public void setExtractQueue(ConcurrentLinkedQueue<Page> extractQueue) {
		this.extractQueue = extractQueue;
	}
}

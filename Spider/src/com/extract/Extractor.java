package com.extract;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dao.CommonDAO;
import com.dao.StoryDao;
import com.entity.Element;
import com.entity.Template;
import com.model.Item;
import com.model.Page;
import com.model.Story;
import com.util.SpringFactory;

public class Extractor extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(Extractor.class);

	private static ConcurrentLinkedQueue<Page> pageQueue = new ConcurrentLinkedQueue<Page>();

	private static Map<String, ArrayList<Template>> templateMap = new HashMap<String, ArrayList<Template>>();

	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");

	private static StoryDao storyDao = SpringFactory.getBean("storyDao");

	private static Lock lock = new ReentrantLock();

	public static void addPage(Page page) {
		pageQueue.add(page);
	}

	public static void addPages(Collection<Page> pages) {
		pageQueue.addAll(pages);
	}

	public Extractor() {
		LOG.info("Init Extractor: {}", getName());

		lock.lock();
		try {
			if (templateMap.size() < 1) {
				List<Template> templates = commonDAO.getAll(Template.class);
				if (templates != null && templates.size() > 0) {
					for (Template template : templates) {
						URL url = null;
						try {
							url = new URL(template.getSource().getUrl());
						} catch (MalformedURLException e) {
							LOG.error("Error URL: {}", template.getSource()
									.getUrl());
							continue;
						}
						String host = url.getHost();

						ArrayList<Template> subTemplates = templateMap
								.get(host);
						if (subTemplates == null) {
							subTemplates = new ArrayList<Template>();
							templateMap.put(host, subTemplates);
						}
						subTemplates.add(template);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Init Extractor Error", e);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Page page = null;
				if ((page = pageQueue.poll()) == null) {
					Thread.sleep(1000);
					continue;
				}
				extract(page);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void extract(Page page) throws Exception {
		String host = page.getUrl().getHost();
		List<Template> templates = templateMap.get(host);
		if (templates == null) {
			return;
		}

		String url = page.getUrl().toString();
		for (Template template : templates) {
			if (template.match(url)) {
				extract(template, page);
				break;
			}
		}
	}

	private void extract(Template template, Page page) throws Exception {
		LOG.info("Extract form {}", page.getUrl());

		if (template.getElements() == null) {
			return;
		}

		Item item = new Item();
		item.setUrl(page.getUrl().toString());
		item.setDate(new Date());
		item.setType(template.getType());
		for (Element element : template.getElements()) {
			extract(item, element, page);
		}
		afterExtract(item);
	}

	private void extract(Item item, Element element, Page page) {
		if (page.getDoc() == null) {
			return;
		}
		try {
			Object[] objs = page.getDoc().evaluateXPath(element.getDefine());
			if (objs == null || objs.length < 1) {
				return;
			}
			String text = "";
			for (Object obj : objs) {
				if (obj instanceof TagNode) {
					text = extractTxt((TagNode) obj);
					if (!text.trim().isEmpty()) {
						break;
					}
				} else if (obj instanceof String) {
					text = obj.toString();
					break;
				}
			}
			text = element.match(text);
			item.addField(element.getName(), text);
		} catch (XPatherException e) {
			LOG.error("Extract {} error from page {}", element.getDefine(),
					page.getUrl().toString());
		}
	}

	@SuppressWarnings("unchecked")
	public static String extractTxt(TagNode node) {
		List<TagNode> children = node.getAllElementsList(true);
		for (TagNode child : children) {
			if (child.getName().equalsIgnoreCase("script")) {
				child.removeAllChildren();
				node.removeChild(child);
			}
		}
		return node.getText().toString();
	}

	private void afterExtract(Item item) throws Exception {
		Story story = new Story();
		story.setCategory("picture");
		story.setDescription(item.getField("title"));
		story.setRefer(item.getUrl());
		story.setDownloadUrlFinal(item.getField("download"));
		story.setDownloadUrl(item.getField("thumb"));
		storyDao.insert(story);
	}

}

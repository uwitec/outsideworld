package com.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.dao.CommonDAO;
import com.dao.ItemDao;
import com.extract.Extract;
import com.model.Item;
import com.model.policy.Param;
import com.model.policy.Topic;
import com.util.Fetcher;

public class MetaSearcher implements Runnable {

	private static final Logger LOG = Logger.getLogger(MetaSearcher.class);

	public static final String KEYWORD = "${KEYWORD}";
	public static final String PAGE = "${PAGE}";
	public static final String OFFSET = "${OFFSET}";

	private static HtmlCleaner htmlCleaner = new HtmlCleaner();
	private static CommonDAO commonDAO = MetaSearcherClient
			.getBean("commonDAO");
	private static Extract extracter = MetaSearcherClient
			.getBean("extractChain");
	private static ItemDao itemDAO = MetaSearcherClient.getBean("itemDao");

	private Fetcher fetcher = new Fetcher();

	private List<Item> generateItems() {
		List<Item> items = new ArrayList<Item>();
		List<Topic> topics = commonDAO.query("from Topic t");
		List<Param> list = commonDAO
				.query("from Param p where p.type = 'metasearch'");
		LOG.info("Generate Items for metasearch");
		for (Topic topic : topics) {
			String options = topic.getOptional();
			for (String option : options.split(";")) {
				String keyword = "";
				if (StringUtils.isNotEmpty(topic.getInclude())) {
					keyword = "\"" + topic.getInclude().replace(";", "\" \"")
							+ "\"";
				}
				keyword += option;
				if (StringUtils.isNotEmpty(topic.getExclude()))
					keyword += "-(" + topic.getExclude().replace(";", ") -(")
							+ ")";
				keyword = keyword.replace(" ", "%20");
				keyword = keyword.replace("\"", "%22");
				for (Param param : list) {
					String url = param.getValue2();
					String metaTitle = param.getValue4();
					int page = Integer.parseInt(param.getValue3());
					for (int i = 0; i < page; i++) {
						int offset = 10 * i;
						String newUrl = url.replace(KEYWORD, keyword);
						newUrl = newUrl.replace(OFFSET,
								Integer.toString(offset));
						newUrl = newUrl.replace(PAGE, String.valueOf(i + 1));
						Item item = new Item();
						item.setUrl(newUrl);
						item.setMetaTitle(metaTitle);
						item.setSourceId(param.getValue5());
						items.add(item);
					}
				}
			}
		}
		return items;
	}

	public void search() {
		List<Item> items = generateItems();
		for (Item item : items) {
			if (item.getUrl() == null) {
				continue;
			}
			try {
				search(item);
			} catch (Exception e) {
				LOG.error("metasearch: " + item.getUrl() + " error");
			}
		}
	}

	private void search(Item item) {
		LOG.info("Metasearch: " + item.getUrl());
		try {
			fetcher.fetch(item);
			String page = new String(item.getRawData(), item.getEncoding());
			TagNode doc = htmlCleaner.clean(page);
			List<String[]> results = getResults(doc, item.getMetaTitle());
			for (String[] result : results) {
				Item metaItem = new Item();
				metaItem.setUrl(result[1]);
				metaItem.setTitle(result[0]);
				metaItem.setType("metasearch");
				metaItem.setSourceId(item.getSourceId());
				processMetaItem(metaItem);
			}
		} catch (Exception e) {
			LOG.error("Search in " + item.getUrl() + " error", e);
		}
	}

	private void processMetaItem(Item item) {
		LOG.info("Search in Metasearch Result: " + item.getUrl());
		try {
			fetcher.fetch(item);
			if (item.getRawData() == null || item.getEncoding() == null) {
				return;
			}
			extracter.process(item);
			item.setCrawlTime(new Date());
			if (StringUtils.isEmpty(item.getTitle())
					|| StringUtils.isEmpty(item.getContent())) {
				return;
			}
			item.setType("metasearch");
			itemDAO.insert(item);
		} catch (Exception e) {
			LOG.error("Processing meta item error", e);
		}
	}

	private List<String[]> getResults(TagNode node, String xpath)
			throws Exception {
		List<String[]> results = new ArrayList<String[]>();
		Object[] objs = node.evaluateXPath(xpath);
		if (objs != null && objs.length > 0) {
			for (Object obj : objs) {
				if (obj instanceof TagNode) {
					String[] result = new String[2];
					result[0] = extractTxt((TagNode) obj);
					result[1] = ((TagNode) obj).getAttributeByName("href");
					results.add(result);
				}
			}
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	private String extractTxt(TagNode node) {
		List<TagNode> children = node.getAllElementsList(true);
		for (TagNode child : children) {
			if (child.getName().equalsIgnoreCase("script")) {
				child.removeAllChildren();
				node.removeChild(child);
			}
		}
		return node.getText().toString();
	}

	@Override
	public void run() {
		LOG.info("Start MetaSearch Thread");
		while (true) {
			search();
			try {
				Thread.sleep(1000 * 3600);
			} catch (InterruptedException e) {
				LOG.error(e.getMessage());
			}
		}
	}
}

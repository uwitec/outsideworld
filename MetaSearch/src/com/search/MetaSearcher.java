package com.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.dao.CommonDAO;
import com.dao.ItemDao;
import com.extract.Extract;
import com.model.Item;
import com.model.policy.Param;
import com.model.policy.Topic;
import com.util.Fetcher;
import com.util.SpringFactory;

public class MetaSearcher implements Runnable {

	public static final String KEYWORD = "${KEYWORD}";
	public static final String OFFSET = "${OFFSET}";

	private static HtmlCleaner htmlCleaner = new HtmlCleaner();
	private static CommonDAO commonDAO = SpringFactory.getBean("commonDAO");
	private static Extract extracter = SpringFactory.getBean("extractChain");
	private static ItemDao itemDAO = SpringFactory.getBean("itemDao");

	private Fetcher fetcher = new Fetcher();

	private List<Item> generateItems() {
		List<Item> items = new ArrayList<Item>();
		// TODO filter
		List<Topic> topics = commonDAO.query("from Topic t");
		List<Param> list = commonDAO
				.query("from Param p where p.type = 'metasearch'");
		for (Topic topic : topics) {
			String keyword = topic.getInclude().replace(";", "+");
			for (Param param : list) {
				String url = param.getValue2();
				String metaTitle = param.getValue4();
				int page = Integer.parseInt(param.getValue3());
				for (int i = 0; i < page; i++) {
					int offset = 10 * i;
					String newUrl = url.replace(KEYWORD, keyword).replace(
							OFFSET, Integer.toString(offset));
					Item item = new Item();
					item.setUrl(newUrl);
					item.setMetaTitle(metaTitle);
					items.add(item);
				}
			}
		}
		return items;
	}

	public void search() {
		List<Item> items = generateItems();
		for (Item item : items) {
			search(item);
		}
	}

	private void search(Item item) {
		try {
			fetcher.fetch(item);
			String page = new String(item.getRawData(), item.getEncoding());
			TagNode doc = htmlCleaner.clean(page);
			List<String[]> results = getResults(doc, item.getMetaTitle());
			for (String[] result : results) {
				Item metaItem = new Item();
				metaItem.setUrl(result[1]);
				metaItem.setTitle(result[0]);
				processMetaItem(metaItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processMetaItem(Item item) {
		try {
			fetcher.fetch(item);
			extracter.process(item);
			item.setType("MetaSearch");
			item.setCrawlTime(new Date());
			itemDAO.insert(item);
		} catch (Exception e) {
			e.printStackTrace();
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
		while (true) {
			search();
			try {
				Thread.sleep(1000 * 3600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

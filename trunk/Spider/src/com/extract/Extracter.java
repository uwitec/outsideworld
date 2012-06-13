package com.extract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.entity.Element;
import com.entity.Template;
import com.model.Item;
import com.model.Page;
import com.util.HtmlCleanUtil;

public class Extracter extends Thread {

	private static final Logger LOG = LoggerFactory.getLogger(Extracter.class);

	private static ConcurrentLinkedQueue<Page> pageQueue = new ConcurrentLinkedQueue<Page>();

	private static Map<String, ArrayList<Template>> templateMap;

	public static void addPage(Page page) {
		pageQueue.add(page);
	}

	public static void addPages(Collection<Page> pages) {
		pageQueue.addAll(pages);
	}

	public Extracter() {
		// TODO
	}

	@Override
	public void run() {
		while (true) {
			try {
				Page page = null;
				if ((page = pageQueue.poll()) == null) {
					LOG.info("Waiting for new Page");
					Thread.sleep(1000);
					continue;
				}
				extract(page);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Item extract(Page page) {
		String host = page.getUrl().getHost();
		List<Template> templates = templateMap.get(host);
		if (templates == null) {
			return null;
		}

		String url = page.getUrl().toString();
		for (Template template : templates) {
			if (template.match(url)) {
				return extract(template, page);
			}
		}
		return null;
	}

	private Item extract(Template template, Page page) {
		Item item = new Item();
		item.setUrl(page.getUrl().toString());
		if(template.getElements()!=null&&template.getElements().size()>0){
		    for(Element element:template.getElements()){
		        String value = "";
                try {
                    value = HtmlCleanUtil.parse(page.getHtml(), element.getDefine(), "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
		        item.addField(element.getName(), value);
		    }
		}
		if(item.isNoField()){
		    return null;
		}
		return item;
	}
}

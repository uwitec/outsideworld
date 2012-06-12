package com.extract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entity.Template;
import com.model.Page;

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

	private void extract(Page page) {
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

	private void extract(Template template, Page page) {
		// TODO
	}
}

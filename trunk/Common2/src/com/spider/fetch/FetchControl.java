package com.spider.fetch;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.model.Page;
public class FetchControl implements Runnable {
	private ConcurrentLinkedQueue<Page> fetchQueue;
	private Fetcher fetcher;

	@Override
	public void run() {
		while (true) {
			Page page = fetchQueue.poll();
			try {
				if (page == null) {
					System.out.println("Fetcher thread sleep!");
					Thread.sleep(1*1000);
				} else {
					fetcher.fetch(page);
				}
			} catch (Exception e) {
                
			}
		}

	}

	public ConcurrentLinkedQueue<Page> getFetchQueue() {
		return fetchQueue;
	}

	public void setFetchQueue(ConcurrentLinkedQueue<Page> fetchQueue) {
		this.fetchQueue = fetchQueue;
	}

	public Fetcher getFetcher() {
		return fetcher;
	}

	public void setFetcher(Fetcher fetcher) {
		this.fetcher = fetcher;
	}
	
}

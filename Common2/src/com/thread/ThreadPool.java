package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	private int threadNum = 1;
	private ExecutorService pool = Executors.newFixedThreadPool(threadNum);

	public void run(Runnable runable) {
		for (int i = 0; i < threadNum; i++) {
			pool.execute(runable);
		}
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
}

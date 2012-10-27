package com.instructure.queue;

import java.util.concurrent.BlockingQueue;

public class MemQueue<T> implements Queue<T> {
    private BlockingQueue<T> queue;
	@Override
	public T poll() {
		// TODO Auto-generated method stub
		return queue.poll();
	}

	@Override
	public void push(T t) {
		queue.add(t);
	}

	public BlockingQueue<T> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<T> queue) {
		this.queue = queue;
	}

}

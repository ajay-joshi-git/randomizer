package com.assignment.randomize.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**

 */
public class QueueManager<T> {
	
	private BlockingQueue<T> queue;
	
	public QueueManager() {
		super();
		this.queue = new LinkedBlockingQueue<T>();
	}

	public BlockingQueue<T> getQueue() {
		return queue;
	}

	public T getPayLoad() throws InterruptedException {
		return queue.take();
	}
	
	public void putData(T t) throws InterruptedException {
		queue.put(t);
	}
	
}

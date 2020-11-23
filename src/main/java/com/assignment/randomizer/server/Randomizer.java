package com.assignment.randomizer.server;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.assignment.randomize.queue.DistributedQueue;


public class Randomizer {
	private DistributedQueue queue;
	
	public Randomizer(int serverPort) {
		queue = new DistributedQueue(serverPort);
	}
	
	public DistributedQueue getDistributedQueue() {
		return queue;
	}
	
	public void execute() {
		ExecutorService senderExecutor = Executors.newSingleThreadExecutor();
		ExecutorService receiverExecutor = Executors.newSingleThreadExecutor();

		//Start a thread to send data to client queue
		senderExecutor.execute(() -> {
			System.out.println("Started.");
				try {
				while (true) {
					try {
						queue.putToInQueue(Math.abs(new Random().nextInt(Integer.MAX_VALUE)));
						
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					} 
				}
			}
			finally {
				senderExecutor.shutdown();
			}
				
		});
		
		//Start a thread to get result from client queue (Printer thread) 
		receiverExecutor.execute(() -> {
			try {
				while (true) {
					try {
						Payload result = queue.getFromOutQueue(); //Blocking
							System.out.println("Random number [" + result.getNum() + "] is "  + ((result.isPrime()) ? "" : "not") + " a prime number" + " : total numbers processed [" + result.getCount() +  "]" );
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}
			}
			finally {
				receiverExecutor.shutdown();
			}
		});

	}
	
}

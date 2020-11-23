package com.assignment.randomizer.server;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
		Executor senderExecutor = Executors.newSingleThreadExecutor();
		Executor receiverExecutor = Executors.newSingleThreadExecutor();

		//Start a thread to send data to client queue
		senderExecutor.execute(() -> {
			System.out.println("Started.");
			while (true) {
				try {
					queue.putToInQueue(Math.abs(new Random().nextInt(Integer.MAX_VALUE)));
					
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		});
		
		//Start a thread to get result from client queue (Printer thread) 
		receiverExecutor.execute(() -> {
			while (true) {
				try {
					Payload result = queue.getFromOutQueue(); //Blocking
						System.out.println("Random number [" + result.getNum() + "] is "  + ((result.isPrime()) ? "" : "not") + " a prime number");
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		});

	}
	
}

package com.assignment.randomizer.server;

import java.util.Random;
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
	    
		//Start a thread to send data to client queue
		new Thread(() -> {
			System.out.println("Started.");
			while (true) {
				try {
					queue.putToInQueue(Math.abs(new Random().nextInt(Integer.MAX_VALUE)));
					
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		}).start();
		
		//Start a thread to get result from client queue (Printer thread) 
		new Thread(() -> {
			while (true) {
				try {
					Payload result = queue.getFromOutQueue(); //Blocking
						System.out.println("Random number [" + result.getNum() + "] is "  + ((result.isPrime()) ? "" : "not") + " a prime number");
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		}).start();

	}
	
}

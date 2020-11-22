package com.assignment.randomizer.runner;

import com.assignment.randomizer.server.Randomizer;

/**
 */
public class RandomizerRunner {
	private static final int SERVER_PORT = 16000;

	public static void main(String[] args) {
		System.out.println("Starting randomizer and distributed queue....");
		Randomizer rr = new Randomizer(SERVER_PORT);
		
		//Start Queue
		rr.getDistributedQueue().startDQ();
		
		//Start Randomizer
		rr.execute();
		
		
	}
}

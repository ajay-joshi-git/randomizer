package com.assignment.randomizer.runner;

import com.assignment.randomizer.server.Randomizer;

/**
 */
public class QueueRunner {
	private static final int SERVER_PORT = 16000;

	public static void main(String[] args) {
		System.out.println("Starting randomizer and distributed queue....");
		new Randomizer(SERVER_PORT).execute();
	}
}

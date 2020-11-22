package com.assignment.randomizer.server;

/*
 *  Info about number is/not prime stored in this pay load
 */
public class Payload {
	
	private final int num;
	private final boolean isPrime;
	
	public Payload(int num, boolean isPrime) {
		this.num = num;
		this.isPrime = isPrime;
	}
	
	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
	/**
	 * @return the isPrime
	 */
	public boolean isPrime() {
		return isPrime;
	}
	
	@Override
	public String toString() {
		return "Payload [num=" + num + ", isPrime=" + isPrime + "]";
	}
	
}

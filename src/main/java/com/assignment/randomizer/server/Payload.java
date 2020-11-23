package com.assignment.randomizer.server;

/*
 *  Info about number is/not prime stored in this pay load
 */
public class Payload {
	
	private final int num;
	private final boolean isPrime;
	private int count;
	
	public Payload(int num, boolean isPrime, int count) {
		this.num = num;
		this.isPrime = isPrime;
		this.count  = count;
	}
	
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Payload [num=" + num + ", isPrime=" + isPrime + ", count=" + count + "]";
	}
	
	
	
}

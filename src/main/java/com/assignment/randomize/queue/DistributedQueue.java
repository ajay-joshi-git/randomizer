package com.assignment.randomize.queue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.assignment.randomizer.server.Payload;

/**

 */
public class DistributedQueue {
	
	private final QueueThread queueThread;
	
	QueueManager<Payload> outQManager;
	QueueManager<Integer> inQManager;
	int count = 0;
	
	public DistributedQueue(int serverPort) {
		queueThread = new QueueThread(serverPort);
		createQueues();
	}
	
	private void createQueues() {
		outQManager = new QueueManager<Payload>();
		inQManager = new QueueManager<Integer>();
	}

	public void startDQ() {
		new Thread(queueThread).start();
	}
	
	public void PrintInQueue() {
		for (Integer i : inQManager.getQueue()) {
		   System.out.print("Random numbers waiting to be processed " + i);	
		}
	}
	
	public void PrintOutQueue() {
		for (Payload p : outQManager.getQueue()) {
			   System.out.print("result waiting to be processed " + p);	
			}
	}
	
	public void putToInQueue(Integer e) throws InterruptedException {
		inQManager.getQueue().put(e);
	}

	public Payload getFromOutQueue() throws InterruptedException {
		return outQManager.getQueue().take();
	}
	
	class QueueThread implements Runnable {
		private final int serverPort;
		DataInputStream in;
		DataOutputStream out;
		
		public QueueThread(int serverPort) {
			this.serverPort = serverPort;
		}

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			Socket socket = null;
			
			System.out.println("Starting queue..");
			
			try {
				serverSocket = new ServerSocket(serverPort);
				while (true) {
					socket = serverSocket.accept();
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());
					
					Runnable runnable = () -> {
						try {
							while (true) {
								try {
									//Here taking from randomizer in queue
									int input = (int) inQManager.getQueue().take();
									
									//Process , get result object 
									Payload result = process(out, in, input);
									
									//put result to out queue
									outQManager.getQueue().put(result);
									
								//	PrintInQueue();
								//	PrintOutQueue();
									
								} catch (InterruptedException e) {
									Thread.currentThread().interrupt();
									return;
								}
							}
						} finally {
							
							try {
								in.close();
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					};	
					
					new Thread(runnable).start();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				System.out.println("Stoping queue");
				try {
					if (serverSocket != null) serverSocket.close();
				} catch (IOException e) {
					System.out.println("Error closing socket");
				}
			}
		}
	}
		
	public Payload process(DataOutputStream out, DataInputStream in, int data) {
		try {
			
			//here sending to client 
			out.writeInt(data);
			++count;
			
			return new Payload(in.readInt(), in.readBoolean(), count);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}

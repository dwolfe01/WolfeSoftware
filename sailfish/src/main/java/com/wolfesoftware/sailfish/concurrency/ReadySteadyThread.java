package com.wolfesoftware.sailfish.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadySteadyThread{

	private int i;
	private WorkerFactory threadFactory;

	public ReadySteadyThread(int i, WorkerFactory threadFactory) {
		this.i = i;
		this.threadFactory=threadFactory;
	}

	/*
	 * This method is the actual bit that starts the threads
	 */
	public void go() {
		ExecutorService executor = Executors.newFixedThreadPool(i);
		//long startTime = System.currentTimeMillis();
		for (int x=0;x<i;x++){
			executor.execute(this.threadFactory.getThread());
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		//System.out.println(System.currentTimeMillis() - startTime);
	}
}


package com.wolfesoftware.sailfish.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.wolfesoftware.sailfish.concurrency.worker.factory.WorkerFactory;

public class ReadySteadyThread{

	private int i;
	private WorkerFactory workerFactory;

	public ReadySteadyThread(int i, WorkerFactory threadFactory) {
		this.i = i;
		this.workerFactory=threadFactory;
	}

	/*
	 * This method is the actual bit that starts the threads
	 */
	public void go() {
		ExecutorService executor = Executors.newFixedThreadPool(i);
		long startTime = System.currentTimeMillis();
		for (int x=0;x<i;x++){
			executor.execute(this.workerFactory.getWorker());
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println(System.currentTimeMillis() - startTime);
	}
}


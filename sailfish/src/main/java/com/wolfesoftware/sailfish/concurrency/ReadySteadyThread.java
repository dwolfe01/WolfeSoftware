package com.wolfesoftware.sailfish.concurrency;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.concurrency.worker.factory.WorkerFactory;

public class ReadySteadyThread {

	private int numberOfThreads;
	private WorkerFactory workerFactory;
	long startTime;
	ThreadPoolExecutor executor;

	public ReadySteadyThread(int i, WorkerFactory threadFactory) {
		this.numberOfThreads = i;
		this.workerFactory = threadFactory;
	}

	/*
	 * This method is the actual bit that starts the threads If the
	 * workerfactory has more work to do then it should call itself again
	 */
	public void go() {
		startTime = System.currentTimeMillis();
		execute();
	}

	private void execute() {
		executor = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		while (!executor.isTerminated()) {
			boolean thereAnyMoreWorkToDo = workerFactory
					.isThereAnyMoreWorkToDo();
			if (!thereAnyMoreWorkToDo) {// nothing left to do so might as shut
										// it down
				executor.shutdown();
			} else if (executor.getActiveCount() < executor
					.getMaximumPoolSize()) {
				// System.out.println("activeCount: " +
				// executor.getActiveCount()
				// + " max Pool size:" + executor.getMaximumPoolSize());
				executor.execute(this.workerFactory.getWorker());
			}
		}
		Logger.info("ReadySteadyThreadCompleted"
				+ (System.currentTimeMillis() - startTime));
	}
}

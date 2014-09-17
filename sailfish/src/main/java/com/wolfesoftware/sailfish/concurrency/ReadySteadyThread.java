package com.wolfesoftware.sailfish.concurrency;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
			if (weHaveAnAvailableThread(executor)) {
				if (!workerFactory.isThereAnyMoreWorkToDo()) {
					executor.shutdown();
				} else {
					addJobToExecutor();
				}
			}
		}
		System.out.println("ReadySteadyThreadCompleted"
				+ (System.currentTimeMillis() - startTime));
	}

	private void addJobToExecutor() {
		try {
			executor.execute(this.workerFactory.getWorker());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean weHaveAnAvailableThread(ThreadPoolExecutor executor) {
		return executor.getActiveCount() < executor.getMaximumPoolSize();
	}
}

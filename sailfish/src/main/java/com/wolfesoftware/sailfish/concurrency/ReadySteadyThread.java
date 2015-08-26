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

	public void go() {
		startTime = System.currentTimeMillis();
		this.execute();
	}

	/* this keeps adding runnable jobs from a workerfactory until the workerfactory indicates there is no more work to do*/
	private void execute() {
		executor = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		while (!executor.isTerminated()) {
			if (weHaveAnAvailableThread(executor)) {
				if (!workerFactory.isThereAnyMoreWorkToDo()) {//race condition here 
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

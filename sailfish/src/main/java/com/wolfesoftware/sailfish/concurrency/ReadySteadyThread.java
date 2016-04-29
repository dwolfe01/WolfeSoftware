package com.wolfesoftware.sailfish.concurrency;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.wolfesoftware.sailfish.concurrency.worker.factory.WorkerFactory;

public class ReadySteadyThread {

	private WorkerFactory workerFactory;
	long startTime;
	ThreadPoolExecutor executor;

	public ReadySteadyThread(int numberOfThreads, WorkerFactory threadFactory) {
		this.workerFactory = threadFactory;
		executor = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}

	public void go() {
		startTime = System.currentTimeMillis();
		this.execute();
	}

	/*
	 * this keeps adding runnable jobs from a workerfactory until the
	 * workerfactory indicates there is no more work to do
	 */
	private void execute() {
		boolean shallIKeepGoing = true;
		while (shallIKeepGoing) {
			if (weHaveAnAvailableThread(executor)) {
				if (workerFactory.isThereAnyMoreWorkToDo()) {
					addJobToExecutor();
				} else {
					shallIKeepGoing = false;
				}
			}
		}
		executor.shutdown();
		while(executor.getActiveCount()!=0){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("ReadySteadyThreadCompleted"
				+ (System.currentTimeMillis() - startTime));
	}

	private void addJobToExecutor() {
		try {
			Runnable worker = this.workerFactory.getWorker();
			executor.execute(worker);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean weHaveAnAvailableThread(ThreadPoolExecutor executor) {
		return executor.getActiveCount() < executor.getMaximumPoolSize();
	}
}

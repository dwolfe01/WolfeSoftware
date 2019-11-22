package com.wolfesoftware.sailfish.core.concurrency;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReadySteadyThread {

	private WorkerFactory workerFactory;
	long startTime;
	ThreadPoolExecutor executor;
	static final Logger Logger = LoggerFactory.getLogger(ReadySteadyThread.class);

	public ReadySteadyThread(int numberOfThreads, WorkerFactory threadFactory) {
		this.workerFactory = threadFactory;
		executor = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}

	public void go() {
		startTime = System.currentTimeMillis();
		this.execute();
		Logger.info("ReadySteadyThreadCompleted"
				+ (System.currentTimeMillis() - startTime));
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
		
	}

	public WorkerFactory getWorkerFactory() {
		return workerFactory;
	}

	public void setWorkerFactory(WorkerFactory workerFactory) {
		this.workerFactory = workerFactory;
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

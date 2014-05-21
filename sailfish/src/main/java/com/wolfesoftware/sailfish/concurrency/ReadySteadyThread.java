package com.wolfesoftware.sailfish.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.concurrency.worker.factory.WorkerFactory;

public class ReadySteadyThread {

	private int i;
	private WorkerFactory workerFactory;
	long startTime;
	ExecutorService executor;

	public ReadySteadyThread(int i, WorkerFactory threadFactory) {
		this.i = i;
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
		executor = Executors.newFixedThreadPool(i);
		for (int x = 0; x < i; x++) {
			executor.execute(this.workerFactory.getWorker());
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		if (workerFactory.isThereAnyMoreWorkToDo()) {
			Logger.info("All threads completed, kicking them off again");
			this.execute();
		} else {
			Logger.info("ReadySteadyThreadCompleted"
					+ (System.currentTimeMillis() - startTime));
		}
	}
}

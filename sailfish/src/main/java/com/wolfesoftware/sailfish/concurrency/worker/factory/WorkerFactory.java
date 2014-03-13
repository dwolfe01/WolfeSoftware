package com.wolfesoftware.sailfish.concurrency.worker.factory;

import com.wolfesoftware.sailfish.worker.Worker;

/*
 * A worker factory returns a worker which a series of parallel units of work as a runnable thread
 */
public abstract class WorkerFactory {

	public abstract Runnable getWorker();

	protected Runnable getAsThread(final Worker worker) {
		return new Thread() {
			@Override
			public void run() {
				worker.go();
			}
		};
	}

}

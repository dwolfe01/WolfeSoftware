package com.wolfesoftware.sailfish.concurrency.worker.factory;

import com.wolfesoftware.sailfish.worker.UnitOfWork;
import com.wolfesoftware.sailfish.worker.Worker;

public class SimpleWorkerFactory extends WorkerFactory {
	private UnitOfWork<?> unitOfWork;

	/*
	 * This class creates a factory from a unitOfWork
	 */
	public SimpleWorkerFactory(UnitOfWork<?> unitOfWork) {
		this.unitOfWork = unitOfWork;
	}

	public Runnable getWorker() {
		final Worker worker = new Worker(unitOfWork);
		return this.getAsThread(worker);
	}

}

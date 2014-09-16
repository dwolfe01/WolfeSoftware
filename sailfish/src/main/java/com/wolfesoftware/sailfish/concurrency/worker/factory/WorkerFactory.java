package com.wolfesoftware.sailfish.concurrency.worker.factory;

import com.wolfesoftware.sailfish.runnable.httpuser.HttpUser;

/*
 * A worker factory returns a worker which a series of parallel units of work as a runnable thread
 * It also can specify whether there is any more work to be done
 */
public abstract class WorkerFactory {

	private boolean isThereAnyMoreWorkToDo = true;

	public abstract HttpUser getWorker() throws Exception;

	public boolean isThereAnyMoreWorkToDo() {
		return isThereAnyMoreWorkToDo;
	}

	public void setIsThereAnyMoreWorkToDo(boolean finished) {
		this.isThereAnyMoreWorkToDo = finished;
	}

}

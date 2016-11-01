package com.wolfesoftware.sailfish.core.concurrency;

import java.io.PrintStream;

/*
 * A worker factory returns a worker which a series of parallel units of work as a runnable thread
 * It also can specify whether there is any more work to be done
 */
public abstract class WorkerFactory {

	private boolean isThereAnyMoreWorkToDo = true;
	PrintStream os = System.out;

	public abstract Runnable getWorker() throws Exception;

	public boolean isThereAnyMoreWorkToDo() {
		return isThereAnyMoreWorkToDo;
	}

	public void setIsThereAnyMoreWorkToDo(boolean finished) {
		this.isThereAnyMoreWorkToDo = finished;
	}

	protected PrintStream getPrintStream() {
		return os;
	}

	public void setPrintStream(PrintStream os) {
		this.os = os;
	}

}

package com.wolfesoftware.sailfish.concurrency;

public interface WorkerFactory {

	Runnable getThread();

}

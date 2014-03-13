package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.io.IOException;

import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.Worker;

public class HttpSessionWorkerFactory extends WorkerFactory {
	/*
	 * This class creates a worker and also returns it as a runnable This will
	 * potentially read from an tomcat access log file to generate different
	 * types of sessions on each call to getWorker
	 */
	public Runnable getWorker() {
		try {
			final Worker httpSession = new Worker(new Request(
					"http://localhost"));
			return this.getAsThread(httpSession);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

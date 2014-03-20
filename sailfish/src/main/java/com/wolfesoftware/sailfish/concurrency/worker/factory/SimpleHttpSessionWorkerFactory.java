package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.Worker;

/*
 * This class creates a worker and also returns it as a runnable This will
 * potentially read from an tomcat access log file to generate different
 * types of sessions on each call to getWorker
 */
public class SimpleHttpSessionWorkerFactory extends WorkerFactory {

	private LogFileReader logFileReader;

	public void setUrls(LogFileReader logFileReader) {
		this.logFileReader = logFileReader;
	}

	public Runnable getWorker() {
		try {
			Iterator<String> iterator = logFileReader.iterator();
			ArrayList<Request> requests = new ArrayList<Request>();
			while (iterator.hasNext()) {
				requests.add(new Request(iterator.next()));
			}
			final Worker httpSession = new Worker(
					requests.toArray(new Request[requests.size()]));
			return this.getAsThread(httpSession);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

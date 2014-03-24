package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.DoNothingRequest;
import com.wolfesoftware.sailfish.worker.httpuser.HttpUser;

/*
 * This class creates a worker and also returns it as a runnable This will
 * potentially reads from an tomcat access log file to generate different
 * types of sessions on each call to getWorker
 */
public class AdvancedHttpSessionWorkerFactory extends WorkerFactory {

	volatile int positionInRequests;
	List<Request> requests = new ArrayList<Request>();
	private int size;

	public void setUrls(LogFileReader logFileReader) throws IOException {
		requests = Collections
				.synchronizedList(logFileReader.getAsListOfUrls());
		size = requests.size();
	}

	private int getSizeOfRequests() {
		return size;
	}

	public Runnable getWorker() {
		HttpUser user = new HttpUser();

		user.establishSession(getNextRequest()).add(getNextRequest())
				.add(getNextRequest()).add(getNextRequest());

		System.out.println("Processing about " + positionInRequests + " of "
				+ getSizeOfRequests() + "requests");
		return this.getAsThread(user);
	}

	private Request getNextRequest() {
		try {
			if (this.isThereAnyMoreWorkToDo()) {
				Request request = requests.get(positionInRequests++);
				if (positionInRequests + 1 > getSizeOfRequests()) {
					this.setIsThereAnyMoreWorkToDo(false);
				}
				return request;
			} else {
				return new DoNothingRequest();
			}
		} catch (Exception e) {
			return null;
		}
	}
}

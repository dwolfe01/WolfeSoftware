package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.request.DoNothingRequest;
import com.wolfesoftware.sailfish.request.Request;
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

	public void setUrls(LogFileReader logFileReader) throws BadLogFileException {
		requests = Collections
				.synchronizedList(logFileReader.getAsListOfUrls());
		size = requests.size();
	}

	private int getSizeOfRequests() {
		return size;
	}

	public Runnable getWorker() {
		HttpUser user = new HttpUser();
		Logger.info("Current position in request list" + positionInRequests
				+ " of " + getSizeOfRequests() + "requests");

		user.establishSession(getNextRequest()).add(getNextRequest())
				.add(getNextRequest()).add(getNextRequest());

		return this.getAsThread(user);
	}

	private Request getNextRequest() {
		if (this.isThereAnyMoreWorkToDo()) {
			Request request = requests.get(positionInRequests++);
			if (positionInRequests + 1 > getSizeOfRequests()) {
				this.setIsThereAnyMoreWorkToDo(false);
			}
			return request;
		} else {
			return new DoNothingRequest();
		}
	}
}

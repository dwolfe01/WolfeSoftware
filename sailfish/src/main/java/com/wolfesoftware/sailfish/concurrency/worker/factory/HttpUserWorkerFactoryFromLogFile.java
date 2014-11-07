package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.requests.GetRequest;
import com.wolfesoftware.sailfish.runnable.httpuser.HttpUser;

/*
 * This class creates a worker and also returns it. This will
 * potentially reads from an tomcat access log file to generate different
 * types of sessions on each call to getWorker
 */
public class HttpUserWorkerFactoryFromLogFile extends WorkerFactory {

	volatile int positionInRequests;
	List<String> requests = new ArrayList<String>();
	private int size;

	public void setUrls(LogFileReader logFileReader) throws BadLogFileException {
		requests = Collections.synchronizedList(logFileReader.getAsListOfUrls());
		size = requests.size();
	}

	private int getSizeOfRequests() {
		return size;
	}

	@Override
	public HttpUser getWorker() {
		System.out.println("Number of requests:" + positionInRequests);
		final HttpUser user = new HttpUser();
		String url = "";
		try {
			for (int x = 0; x < 4; x++) {
				url = getNextRequest();
				user.addGetRequest(new GetRequest(url));
			}
		} catch (MalformedURLException e) {
			System.out.println("Problem with: " + url);
			e.printStackTrace();
		}
		return user;
	}

	private synchronized String getNextRequest() {
		if (this.isThereAnyMoreWorkToDo()) {
			if (positionInRequests + 1 >= getSizeOfRequests()) {
				this.setIsThereAnyMoreWorkToDo(false);
			}
			return requests.get(positionInRequests++);
		} else {
			return "";
		}
	}
}

package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.Worker;
import com.wolfesoftware.sailfish.worker.httpuser.HttpUser;

/*
 * This class creates a worker and also returns it as a runnable This will
 * potentially reads from an tomcat access log file to generate different
 * types of sessions on each call to getWorker
 */
public class AdvancedHttpSessionWorkerFactory extends WorkerFactory {

	volatile int positionInRequests;
	ArrayList<Request> requests;

	public void setUrls(LogFileReader logFileReader) throws IOException {
		Iterator<String> iterator = logFileReader.iterator();
		requests = new ArrayList<Request>();
		while (iterator.hasNext()) {
			requests.add(new Request(iterator.next()));
		}
	}

	public Runnable getWorker() {
			HttpUser user = new HttpUser();
			user.establishSession(getNextRequest()).add(getNextRequest()).add(getNextRequest()).add(getNextRequest());
			return this.getAsThread(user);
	}

	private Request getNextRequest() {
		try{
			 return requests.get(positionInRequests++);
		}catch (Exception e){
			System.out.println(this.getClass().getCanonicalName() + " finished");
			return null;
		}
	}
}

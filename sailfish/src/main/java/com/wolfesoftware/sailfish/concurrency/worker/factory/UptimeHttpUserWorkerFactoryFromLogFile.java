package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.requests.GetRequest;
import com.wolfesoftware.sailfish.runnable.httpuser.UptimeHttpUser;
import com.wolfesoftware.sailfish.runnable.httpuser.UptimeHttpUserFactory;


public class UptimeHttpUserWorkerFactoryFromLogFile extends WorkerFactory {

	volatile int positionInRequests;
	List<String> requests = new ArrayList<String>();
	private UptimeHttpUserFactory httpUserFactory;
	private int size;
	
	public UptimeHttpUserWorkerFactoryFromLogFile() {
		this.httpUserFactory = new UptimeHttpUserFactory();
	}
	
	public UptimeHttpUserWorkerFactoryFromLogFile(UptimeHttpUserFactory httpUserFactory) {
		this.httpUserFactory = httpUserFactory;
	}
	

	public void setUrls(LogFileReader logFileReader) throws BadLogFileException {
		requests = Collections.synchronizedList(logFileReader.getAsListOfUrls());
		size = requests.size();
	}

	@Override
	public UptimeHttpUser getWorker() {
		final UptimeHttpUser user = httpUserFactory.getHttpUser();
		String url = "";
		try {
			for (int x = 0; x < size; x++) {
				user.addGetRequest(new GetRequest(requests.get(x)));
			}
		} catch (MalformedURLException e) {
			System.out.println("Problem with: " + url);
			e.printStackTrace();
		}
		return user;
	}
	

}

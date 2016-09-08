package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.requests.GetRequest;
import com.wolfesoftware.sailfish.runnable.httpuser.UptimeHttpUser;
import com.wolfesoftware.sailfish.uptime.UptimeHistory;


public class UptimeHttpUserWorkerFactoryFromLogFile extends WorkerFactory {

	volatile int positionInRequests;
	List<String> requests = new ArrayList<String>();
	private int size;
	private UptimeHistory uptimeHistory = new UptimeHistory();

	public void setUrls(LogFileReader logFileReader) throws BadLogFileException {
		requests = Collections.synchronizedList(logFileReader.getAsListOfUrls());
		size = requests.size();
	}

	@Override
	public UptimeHttpUser getWorker() {
		final UptimeHttpUser user = new UptimeHttpUser(uptimeHistory);
		String url = "";
		try {
			for (int x = 0; x < size; x++) {
				user.addGetRequest(new GetRequest(requests.get(x)));
			}
		} catch (URISyntaxException e) {
			System.out.println("Problem with: " + url);
			e.printStackTrace();
		}
		return user;
	}
	

}

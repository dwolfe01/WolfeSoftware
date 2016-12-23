package com.wolfesoftware.sailfish.http.worker.factory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wolfesoftware.sailfish.core.concurrency.WorkerFactory;
import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.http.requests.GetRequest;
import com.wolfesoftware.sailfish.http.runnable.httpuser.HttpUser;
import com.wolfesoftware.sailfish.http.runnable.httpuser.UptimeHttpUser;
import com.wolfesoftware.sailfish.http.uptime.UptimeHistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UptimeHttpUserWorkerFactoryFromLogFile extends WorkerFactory {

	volatile int positionInRequests;
	List<String> requests = new ArrayList<String>();
	private int size;
	private UptimeHistory uptimeHistory = new UptimeHistory();
	final Logger Logger = LoggerFactory.getLogger(UptimeHttpUserWorkerFactoryFromLogFile.class);
	
	public UptimeHttpUserWorkerFactoryFromLogFile(LogFileReader logFileReader) throws BadLogFileException {
		requests = Collections.synchronizedList(logFileReader.getAsListOfUrls());
		size = requests.size();
	}

	@Override
	public HttpUser getWorker() {
		final HttpUser user = new UptimeHttpUser(uptimeHistory);
		String url = "";
		try {
			for (int x = 0; x < size; x++) {
				user.addGetRequest(new GetRequest(requests.get(x)));
			}
		} catch (URISyntaxException e) {
			Logger.info("Problem with: " + url);
			e.printStackTrace();
		}
		return user;
	}
		

}

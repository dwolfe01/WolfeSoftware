package com.wolfesoftware.sailfish.http.worker.factory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.sailfish.core.concurrency.WorkerFactory;
import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.http.requests.GetRequest;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.runnable.httpuser.HttpUser;

/* SINGLETON (not forced) and not threadsafe
 * This class creates a worker and also returns it. This will
 * potentially reads from an tomcat access log file to generate different
 * types of sessions on each call to getWorker
 */
public class HttpUserWorkerFactoryFromLogFile extends WorkerFactory {

	volatile int positionInRequests;
	List<String> requests = new ArrayList<String>();
	private int size;
	static final Logger Logger = LoggerFactory.getLogger(HttpUserWorkerFactoryFromLogFile.class);
	private ResponseHandlerFactory responseHandlerFactory;

	public HttpUserWorkerFactoryFromLogFile(LogFileReader logFileReader, ResponseHandlerFactory responseHandlerFactory) throws BadLogFileException {
		this.responseHandlerFactory = responseHandlerFactory;
		requests = Collections.synchronizedList(logFileReader.getAsListOfUrls());
		size = requests.size();
	}

	private int getSizeOfRequests() {
		return size;
	}

	@Override
	public HttpUser getWorker() {
		final HttpUser user = new HttpUser(responseHandlerFactory);
		String url = "";
		try {
			for (int x = 0; x < 4; x++) {
				url = getNextRequest();
				if (!url.equals(""))
				user.addGetRequest(url);
			}
		} catch (URISyntaxException e) {
			Logger.info("Problem with: " + url);
			e.printStackTrace();
		}
		return user;
	}

	private String getNextRequest() {
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

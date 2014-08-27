package com.wolfesoftware.sailfish.runnable.httpuser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.wolfesoftware.sailfish.responsehandler.factory.ResponseHandlerFactory;

/*
 * A fluent API for creating a user http session, this creates a session and runs through a list of requests in order observing any wait times
 */

public class HttpUser implements Runnable {

	private static volatile long numberOfTimesIHaveBeenCreatedRoughly;
	private static volatile long totalExceutionTime;
	private HttpClient httpClient;
	List<String> requests = new ArrayList<String>();
	private long waitTime = 0;
	private ResponseHandlerFactory responseHandlerFactory = new ResponseHandlerFactory(
			"SystemOut");

	public HttpUser() {
		HttpUser.numberOfTimesIHaveBeenCreatedRoughly++;
		httpClient = HttpClients.createDefault();
	}

	public HttpUser(HttpClient httpClient) {
		HttpUser.numberOfTimesIHaveBeenCreatedRoughly++;
		this.httpClient = httpClient;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		for (String request : requests) {
			makeRequest(startTime, request);
			pause();
		}
		long executionTime = System.currentTimeMillis() - startTime;
		HttpUser.totalExceutionTime += executionTime;
	}

	private void makeRequest(long startTime, String request) {
		ResponseHandler<StatusLine> responseHandler = responseHandlerFactory
				.getInstanceOfResponseHandler();
		try {
			StatusLine statusLine = httpClient.execute(new HttpGet(request),
					responseHandler);
			doOutput(startTime, request, statusLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doOutput(long startTime, String request, StatusLine statusLine) {
		System.out.println(statusLine + " " + request + " took "
				+ (System.currentTimeMillis() - startTime));
	}

	public HttpUser add(String request) {
		if (request != null && !request.equals("")) {
			requests.add(request);
		}
		return this;
	}

	public HttpUser setWaitTimeInMilliseconds(long sleepTime) {
		this.waitTime = sleepTime;
		return this;
	}

	private void pause() {
		if (waitTime > 0) {
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

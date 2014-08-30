package com.wolfesoftware.sailfish.runnable.httpuser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private HttpClient httpClient;
	List<String> requests = new ArrayList<String>();

	private long waitTime = 0;

	public HttpUser() {
		httpClient = HttpClients.createDefault();
	}

	public HttpUser(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		for (String request : requests) {
			makeRequest(request);
			pause();
		}
		long executionTime = System.currentTimeMillis() - startTime;
		System.out.println(this.id + " completed " + executionTime);
	}

	private void makeRequest(String request) {
		long startTime = System.currentTimeMillis();
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory
				.getInstanceOfResponseHandler();
		try {
			StatusLine statusLine = httpClient.execute(new HttpGet(request),
					responseHandler);
			doOutput(startTime, request, statusLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HttpUser add(String request) throws MalformedURLException {
		if (request != null && !request.equals("")) {
			new URL(request);
			requests.add(request);
		}
		return this;
	}

	public HttpUser setWaitTimeInMilliseconds(long sleepTime) {
		this.waitTime = sleepTime;
		return this;
	}

	public List<String> getRequests() {
		return requests;
	}

	public void setRequests(List<String> requests) {
		this.requests = requests;
	}

	public String getRequest(int index) {
		return requests.get(index);
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

	private void doOutput(long startTime, String request, StatusLine statusLine) {
		System.out.println(statusLine + " " + request + " took "
				+ (System.currentTimeMillis() - startTime));
	}
}

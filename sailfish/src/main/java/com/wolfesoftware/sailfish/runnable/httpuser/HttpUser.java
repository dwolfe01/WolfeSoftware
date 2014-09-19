package com.wolfesoftware.sailfish.runnable.httpuser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import com.wolfesoftware.sailfish.requests.GetRequest;
import com.wolfesoftware.sailfish.requests.PostRequest;
import com.wolfesoftware.sailfish.requests.Request;

/*
 * A fluent API for creating a user http session, this creates a session and runs through a list of requests in order observing any wait times
 */

public class HttpUser implements Runnable {

	protected String id;
	protected String clientIP = "";
	private HttpClient httpClient;
	private List<Request> requests = new ArrayList<Request>();
	private long waitTime = 0;

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HttpUser() {
		httpClient = HttpClients.createDefault();
	}

	public HttpUser(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		for (Request request : requests) {
			makeRequest(request);
			pause();
		}
		long executionTime = System.currentTimeMillis() - startTime;
		System.out.println(this.id + " completed " + executionTime);
	}

	protected void makeRequest(Request request) {
		long startTime = System.currentTimeMillis();
		try {
			StatusLine statusLine = request.makeRequest(httpClient);
			doOutput(startTime, request, statusLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HttpUser addGetRequest(GetRequest request) throws MalformedURLException {
		requests.add(request);
		return this;
	}

	public HttpUser setWaitTimeInMilliseconds(long sleepTime) {
		this.waitTime = sleepTime;
		return this;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<String> uris) throws MalformedURLException {
		for (String uri : uris) {
			this.addGetRequest(new GetRequest(uri));
		}
	}

	public Request getRequest(int index) {
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

	private void doOutput(long startTime, Request request, StatusLine statusLine) {
		System.out.println(statusLine + " " + request + " took " + (System.currentTimeMillis() - startTime));
	}

	public HttpUser addPostRequest(PostRequest request) throws MalformedURLException {
		requests.add(request);
		return this;
	}
}

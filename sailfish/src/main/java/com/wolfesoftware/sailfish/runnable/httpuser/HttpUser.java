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
	protected HttpClient httpClient;
	protected List<Request> requests = new ArrayList<Request>();
	private long waitTime = 0;

	public HttpUser() {
		httpClient = HttpClients.createDefault();
	}

	public HttpUser(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setRequests(List<String[]> uris) throws MalformedURLException {
		for (String[] uri : uris) {
			if (uri.length == 1) {
				this.addGetRequest(new GetRequest(uri[0]));
			} else {
				PostRequest pr = new PostRequest(uri[0]);
				for (int x = 2; x < uri.length; x++) {
					String[] nameValue = uri[x].split(":");
					pr.addNameValuePostPair(nameValue[0], nameValue[1]);
				}
				this.addPostRequest(pr);
			}
		}
	}

	public Request getRequest(int index) {
		return requests.get(index);
	}

	protected void pause() {
		if (waitTime > 0) {
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doOutput(long startTime, Request request, StatusLine statusLine) {
		System.out.println(statusLine + " " + request + " took " + getTimeTaken(startTime));
	}

	protected long getTimeTaken(long startTime) {
		return System.currentTimeMillis() - startTime;
	}

	public HttpUser addPostRequest(PostRequest request) throws MalformedURLException {
		requests.add(request);
		return this;
	}
}

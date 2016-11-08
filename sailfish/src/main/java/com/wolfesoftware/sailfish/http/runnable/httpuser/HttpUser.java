package com.wolfesoftware.sailfish.http.runnable.httpuser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.StatusLine;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.sailfish.http.requests.AbstractRequest;
import com.wolfesoftware.sailfish.http.requests.GetRequest;
import com.wolfesoftware.sailfish.http.requests.PostRequest;
/*
 * A fluent API for creating a user http session, this creates a session and runs through a list of requests in order observing any wait times
 */

public class HttpUser implements Runnable {

	protected String id;

	protected String clientIP = "";
	protected CloseableHttpClient httpClient;
	protected List<AbstractRequest> requests = new ArrayList<AbstractRequest>();
	private long waitTime = 0;
	
	static final Logger Logger = LoggerFactory.getLogger(HttpUser.class);

	public HttpUser() {
		httpClient = HttpClients.createDefault();
	}

	//purely for test purposes
	public HttpUser(CloseableHttpClient httpClient) {
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
		for (AbstractRequest request : requests) {
			makeRequest(request);
			pause();
		}
		long executionTime = System.currentTimeMillis() - startTime;
		close();
	}

	protected void makeRequest(AbstractRequest request) {
		long startTime = System.currentTimeMillis();
		try {
			StatusLine statusLine = request.makeRequest(httpClient);
			doOutput(startTime, request, statusLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HttpUser addGetRequest(GetRequest request) {
		requests.add(request);
		return this;
	}

	public HttpUser setWaitTimeInMilliseconds(long sleepTime) {
		this.waitTime = sleepTime;
		return this;
	}

	public List<AbstractRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<String[]> uris) throws URISyntaxException {
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

	public AbstractRequest getRequest(int index) {
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

	protected void doOutput(long startTime, AbstractRequest request, StatusLine statusLine) {
		Logger.info(statusLine + " " + request + " took " + getTimeTaken(startTime));
	}

	protected long getTimeTaken(long startTime) {
		return System.currentTimeMillis() - startTime;
	}

	public HttpUser addPostRequest(PostRequest request) {
		requests.add(request);
		return this;
	}

	protected void close() {
		try {
			this.httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

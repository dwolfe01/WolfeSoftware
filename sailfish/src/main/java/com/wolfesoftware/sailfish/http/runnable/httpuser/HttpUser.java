package com.wolfesoftware.sailfish.http.runnable.httpuser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.StatusLine;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.sailfish.http.requests.AbstractRequest;
import com.wolfesoftware.sailfish.http.requests.GetRequest;
import com.wolfesoftware.sailfish.http.requests.PostRequest;
/*
 * A fluent API for creating a user http session, this creates a session and runs through a list of requests in order observing any wait times
 */
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;

public class HttpUser implements Runnable {

	protected String id;
	protected CloseableHttpClient httpClient;
	protected List<AbstractRequest> requests = new ArrayList<AbstractRequest>();
	private long waitTime = 0;
	protected ResponseHandlerFactory responseHandlerFactory;
	private HttpClientFactory httpClientFactory;
	
	static final Logger Logger = LoggerFactory.getLogger(HttpUser.class);

	public HttpUser() {
		this(new ResponseHandlerFactory());
	}

	public HttpUser(ResponseHandlerFactory responseHandlerFactory) {
		httpClientFactory = new HttpClientFactory();
		this.responseHandlerFactory = responseHandlerFactory;
		httpClient = httpClientFactory.getHttpClient();
	}

	// purely for test purposes
	public HttpUser(CloseableHttpClient httpClient, ResponseHandlerFactory responseHandlerFactory) {
		this(responseHandlerFactory);
		this.httpClient = httpClient;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ResponseHandlerFactory getResponseHandlerFactory() {
		return responseHandlerFactory;
	}
	
	public void setResponseHandlerFactory(ResponseHandlerFactory responseHandlerFactory) {
		this.responseHandlerFactory = responseHandlerFactory;
	}
	
	public void run() {
		for (AbstractRequest request : requests) {
			makeRequest(request);
			pause();
		}
		close();
	}

	protected void makeRequest(AbstractRequest request) {
		long startTime = System.currentTimeMillis();
		try {
			outputCookieLogging();
			StatusLine statusLine = request.makeRequest(httpClient, responseHandlerFactory);
			doOutput(startTime, request, statusLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HttpUser addGetRequest(GetRequest getrequest) throws URISyntaxException {
		requests.add(getrequest);
		return this;
	}
	
	public HttpUser addGetRequest(String uri) throws URISyntaxException {
		requests.add(new GetRequest(uri));
		return this;
	}
	
	public HttpUser addPostRequest(PostRequest request) {
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
	
	public AbstractRequest getRequest(int index) {
		return requests.get(index);
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

	protected void close() {
		try {
			this.httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void outputCookieLogging() {
		Logger.info("Cookies...");
		for (Cookie cookie:this.httpClientFactory.getCookieStore().getCookies()){
			Logger.info("Cookie name:" + cookie.getName() + " Cookie domain:" + cookie.getDomain() + " Cookie value:" + cookie.getValue());
		}
	}


}

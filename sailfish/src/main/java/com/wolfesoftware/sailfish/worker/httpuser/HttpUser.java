package com.wolfesoftware.sailfish.worker.httpuser;

import java.util.ArrayList;
import java.util.List;

import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.Worker;

/*
 * A fluent API for creating a user http session, this creates a session and runs through a list of requests in order observing any wait times
 */
public class HttpUser extends Worker {

	Request establishSessionRequest = null;
	List<Request> session = new ArrayList<Request>();
	private long waitTime = 0;
	CookieTin cookieTin = new CookieTin();
	protected String referer;

	public void go() {
		long startTime = System.currentTimeMillis();
		// establish session
		if (establishSessionRequest != null) {
			makeRequestAndSetReferer(establishSessionRequest);
		}
		// perform requests
		for (Request request : session) {
			makeRequestAndSetReferer(request);
		}
		Logger.info(doOutput(startTime));
	}

	private void makeRequestAndSetReferer(Request request) {
		request.setReferer(this.getReferer());
		String responseCode = request.go();
		if (responseCode.startsWith("2")) {
			this.setReferer(request.getUrl());
		}
		pause();
	}

	private void setReferer(String url) {
		this.referer = url;
	}

	public HttpUser add(Request request) {
		request.setCookieTin(cookieTin);
		session.add(request);
		return this;
	}

	public HttpUser establishSession(Request request) {
		establishSessionRequest = request;
		establishSessionRequest.setCookieTin(cookieTin);
		return this;
	}

	public HttpUser setWaitTimeInMilliseconds(long sleepTime) {
		this.waitTime = sleepTime;
		return this;
	}

	public void pause() {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String doOutput(long startTime) {
		String output = "HttpUser Session Time:"
				+ (System.currentTimeMillis() - startTime) + " milliseconds";
		return output;
	}

	public String getReferer() {
		return (null != referer) ? referer : "";
	}
}

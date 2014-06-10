package com.wolfesoftware.sailfish.worker.httpuser;

import java.util.ArrayList;
import java.util.List;

import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.Worker;
import com.wolfesoftware.sailfish.worker.loggable.Loggable;

/*
 * A fluent API for creating a user http session, this creates a session and runs through a list of requests in order observing any wait times
 */
public class HttpUser extends Worker implements Loggable {

	private static volatile long numberOfTimesIHaveBeenCreatedRoughly;

	Request establishSessionRequest = null;
	List<Request> session = new ArrayList<Request>();
	private long waitTime = 0;
	private long loggingThreshold = 6000;
	CookieTin cookieTin = new CookieTin();
	protected String referer;

	public HttpUser() {
		this.numberOfTimesIHaveBeenCreatedRoughly++;
	}

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
		doOutput(startTime);
	}

	private void makeRequestAndSetReferer(Request request) {
		request.setReferer(this.getReferer());
		String responseCode = request.go();
		if (responseCode.startsWith("2")) {
			this.setReferer(request.getUrl());
		} else {
			Logger.info("$$$$$ Response code: " + responseCode + " URL" + request.getUrl());
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

	private void doOutput(long startTime) {
		long processingTime = System.currentTimeMillis() - startTime;
		if (processingTime > loggingThreshold) {
			String output = "HttpUser Session Time:" + processingTime
					+ " milliseconds";
			Logger.info(output);
			String urls = "";
			if (null != establishSessionRequest) {
				urls += establishSessionRequest.getUrl();
			}
			// perform requests
			for (Request request : session) {
				if (null != request) {
					urls += request.getUrl();
				}
			}
			Logger.info(urls);
			Logger.info("***** Number of Sessions = "
					+ numberOfTimesIHaveBeenCreatedRoughly);
		}
	}

	public String getReferer() {
		return (null != referer) ? referer : "";
	}

	public long getLoggingThreshold() {
		return loggingThreshold;
	}

	public void setLoggingThreshold(long loggingThreshold) {
		this.loggingThreshold = loggingThreshold;
	}
}

package com.wolfesoftware.sailfish.worker.httpuser;

import java.util.ArrayList;
import java.util.List;

import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.Worker;
import com.wolfesoftware.sailfish.worker.loggable.Loggable;

/*
 * A fluent API for creating a user http session, this creates a session and runs through a list of requests in order observing any wait times
 * This combines too much logging reponsibility
 */
public class HttpUser extends Worker implements Loggable {

	private static volatile long numberOfTimesIHaveBeenCreatedRoughly;// these
																		// statics
																		// mean
																		// that
																		// HttpUser
																		// can
																		// only
																		// be
																		// used
																		// by
																		// one
																		// worker
																		// factory
																		// within
																		// a JVM
																		// -
																		// this
																		// is
																		// bad
	private static volatile long totalExceutionTime;

	Request establishSessionRequest = null;
	List<Request> session = new ArrayList<Request>();
	private long waitTime = 0;
	private long loggingThreshold = 10000;// 10 seconds
	CookieTin cookieTin = new CookieTin();
	protected String referer;

	public HttpUser() {
		HttpUser.numberOfTimesIHaveBeenCreatedRoughly++;
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
		long executionTime = System.currentTimeMillis() - startTime;
		HttpUser.totalExceutionTime += executionTime;
		doOutput(executionTime);
	}

	private void makeRequestAndSetReferer(Request request) {
		request.setReferer(this.getReferer());
		String responseCode = request.go();
		if (responseCode.startsWith("2")) {
			this.setReferer(request.getUrl());
		} else {
			if (!responseCode.equals("404"))//not really bothered about 404s here 
			Logger.info("$$$$$ Response code: " + responseCode + " URL"
					+ request.getUrl());
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

	private void doOutput(long executionTime) {
		if (executionTime > loggingThreshold) {
			String output = "HttpUser Session Time:" + executionTime
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

	public static double getThroughPutPerHttpUserInSeconds() {
		System.out.println("HttpUser.numberOfTimesIHaveBeenCreatedRoughly"+HttpUser.numberOfTimesIHaveBeenCreatedRoughly);
		System.out.println("HttpUser.totalExceutionTime"+HttpUser.totalExceutionTime);
		if (HttpUser.numberOfTimesIHaveBeenCreatedRoughly>0 && HttpUser.totalExceutionTime>0){//protect against a divide by zero
			return HttpUser.totalExceutionTime / HttpUser.numberOfTimesIHaveBeenCreatedRoughly;
		} 
		return 0.0;
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

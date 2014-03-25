package com.wolfesoftware.sailfish.worker.httpuser;

import java.util.ArrayList;
import java.util.List;

import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.Worker;

/*
 * A fluent API for creating a user http session
 */
public class HttpUser extends Worker {

	Request establishSessionRequest = null;
	List<Request> session = new ArrayList<Request>();
	private long waitTime = 0;

	public void go() {
		// establish session
		if (establishSessionRequest != null) {
			establishSessionRequest.go();
			pause();
		}
		// perform requests
		for (Request request : session) {
			request.go();
			pause();
		}
	}

	public HttpUser add(Request request) {
		session.add(request);
		return this;
	}

	public HttpUser establishSession(Request request) {
		establishSessionRequest = request;
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

}

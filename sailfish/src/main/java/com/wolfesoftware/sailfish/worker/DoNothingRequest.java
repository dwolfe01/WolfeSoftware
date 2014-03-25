package com.wolfesoftware.sailfish.worker;

import com.wolfesoftware.sailfish.request.Request;

public class DoNothingRequest extends Request {

	public DoNothingRequest() {
		super();
	}

	@Override
	public String go() {
		System.out.println("Doing Nothing...");
		return "";
	}
}

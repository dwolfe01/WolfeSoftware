package com.wolfesoftware.sailfish.worker;

import java.io.IOException;

import com.wolfesoftware.sailfish.request.Request;

public class DoNothingRequest extends Request {

	public DoNothingRequest() throws IOException {
		super("");
	}

	@Override
	public String go() {
		System.out.println("Doing Nothing...");
		return null;
	}
}

package com.wolfesoftware.sailfish.worker;

import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.request.Request;

public class DoNothingRequest extends Request {

	public DoNothingRequest() {
		super();
	}

	@Override
	public String go() {
		Logger.info("Doing Nothing...");
		return "";
	}
}

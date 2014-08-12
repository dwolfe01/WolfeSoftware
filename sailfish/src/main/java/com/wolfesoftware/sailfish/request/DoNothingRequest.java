package com.wolfesoftware.sailfish.request;

import org.pmw.tinylog.Logger;

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

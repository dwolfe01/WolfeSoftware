package com.wolfesoftware.sailfish.worker.httpuser;

import org.pmw.tinylog.LoggingLevel;
import org.pmw.tinylog.writers.LoggingWriter;

public class TestLogWriter implements LoggingWriter {

	private String message = "";

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(LoggingLevel arg0, String arg1) {
		setMessage(arg1);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
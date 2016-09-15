package com.wolfesoftware.sailfish.http.logfilereader.exceptions;

public class BadLogFileException extends Exception {

	public BadLogFileException(String msg) {
		super("This is a bad log file" + msg);
	}

}

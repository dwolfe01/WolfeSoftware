package com.wolfesoftware.sailfish.logfilereader.exceptions;

public class BadLogFileException extends Exception {

	public BadLogFileException(String msg) {
		super("This is a bad log file" + msg);
	}

}

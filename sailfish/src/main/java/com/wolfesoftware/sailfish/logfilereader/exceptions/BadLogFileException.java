package com.wolfesoftware.sailfish.logfilereader.exceptions;

public class BadLogFileException extends Exception {

	public BadLogFileException() {
		super("This is a bad log file - it probably has malformed urls in it");
	}

}

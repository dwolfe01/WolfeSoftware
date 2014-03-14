package com.wolfesoftware.sailfish.logfilereader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

//TODO: make this an integration test?
public class LogFileReader {

	List<String> lines;

	public LogFileReader(File logFile) throws IOException {
		lines = FileUtils.readLines(logFile);
	}

	public int getSize() {
		return lines.size();
	}

	public String get(int index) {
		return lines.get(index);
	}

	public Iterator<String> iterator() {
		return (Iterator<String>) lines.iterator();
	}

}

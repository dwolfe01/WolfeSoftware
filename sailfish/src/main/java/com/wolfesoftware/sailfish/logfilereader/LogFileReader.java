package com.wolfesoftware.sailfish.logfilereader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.wolfesoftware.sailfish.request.Request;

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

	public List<Request> getAsListOfUrls() throws IOException {
		List<Request> requests = new ArrayList<Request>();
		for (int x = 0; x < lines.size(); x++) {
			requests.add(new Request(lines.get(x)));
		}
		return requests;
	}
}

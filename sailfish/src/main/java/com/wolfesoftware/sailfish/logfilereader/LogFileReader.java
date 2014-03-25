package com.wolfesoftware.sailfish.logfilereader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.request.Request;

//TODO: make this an integration test?
public class LogFileReader {

	List<String> lines;

	public LogFileReader(File logFile) throws BadLogFileException {
		try {
			lines = FileUtils.readLines(logFile);
		} catch (IOException e) {
			throw new BadLogFileException();
		}
	}

	public int getSize() {
		return lines.size();
	}

	public String get(int index) {
		return lines.get(index);
	}

	public List<Request> getAsListOfUrls() throws BadLogFileException {
		List<Request> requests = new ArrayList<Request>();
		for (int x = 0; x < lines.size(); x++) {
			try {
				requests.add(new Request(lines.get(x)));
			} catch (IOException e) {
				throw new BadLogFileException();
			}
		}
		return requests;
	}
}

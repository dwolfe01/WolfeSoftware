package com.wolfesoftware.sailfish.logfilereader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;

public class LogFileReader {

	List<String> lines;

	public LogFileReader(File logFile) throws BadLogFileException {
		try {
			lines = FileUtils.readLines(logFile);
		} catch (IOException e) {
			throw new BadLogFileException(e.getMessage());
		}
	}

	public int getSize() {
		return lines.size();
	}

	public String get(int index) {
		return lines.get(index);
	}

	public List<String> getAsListOfUrls() throws BadLogFileException {
		List<String> requests = new ArrayList<String>();
		for (int x = 0; x < lines.size(); x++) {
			requests.add(lines.get(x));
		}
		return requests;
	}

	public Map<String, String> getAsMapOfUserNamesPasswords()
			throws BadLogFileException {
		Map<String, String> requests = new HashMap<String, String>();
		for (int x = 0; x < lines.size(); x++) {
			String usernamePassword = lines.get(x);
			String[] login = usernamePassword.split(",");
			String username = login[0];
			String password = login[1];
			requests.put(username, password);
		}
		return requests;
	}
}

package com.wolfesoftware.sailfish.logfilereader;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;

public class LogFileReaderTest {

	@Test
	public void shouldReadLogFileAndOutputSize() throws BadLogFileException {
		File logFile = new File(getClass().getClassLoader()
				.getResource("urls.log").getFile());
		LogFileReader logFileReader = new LogFileReader(logFile);
		assertEquals(6, logFileReader.getSize());
	}

	@Test
	public void shouldReturnUrlsAsListOfRequests() throws BadLogFileException,
			IOException {
		File logFile = new File(getClass().getClassLoader()
				.getResource("urls.log").getFile());
		LogFileReader logFileReader = new LogFileReader(logFile);
		List<String> asListOfUrls = logFileReader.getAsListOfUrls();
		assertEquals(6, asListOfUrls.size());
	}

	@Test
	public void shouldReadFirstAndLastStringFromLogFile()
			throws BadLogFileException {
		File logFile = new File(getClass().getClassLoader()
				.getResource("urls.log").getFile());
		LogFileReader logFileReader = new LogFileReader(logFile);
		assertEquals("http://www.google.co.uk", logFileReader.get(0));
		assertEquals("htp://www.theguardian.com",
				logFileReader.get(logFileReader.getSize() - 1));
	}

}

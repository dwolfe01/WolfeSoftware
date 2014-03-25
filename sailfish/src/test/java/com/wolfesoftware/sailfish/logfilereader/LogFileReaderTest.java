package com.wolfesoftware.sailfish.logfilereader;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.request.Request;

//TODO: make this an integration test?
public class LogFileReaderTest {

	@Test
	public void shouldReadLogFileAndOutputSize() throws BadLogFileException {
		File logFile = new File(getClass().getClassLoader()
				.getResource("urls.log").getFile());
		LogFileReader logFileReader = new LogFileReader(logFile);
		assertEquals(6, logFileReader.getSize());
	}

	@Test
	public void shouldReadFirstAndLastStringFromLogFile()
			throws BadLogFileException {
		File logFile = new File(getClass().getClassLoader()
				.getResource("urls.log").getFile());
		LogFileReader logFileReader = new LogFileReader(logFile);
		assertEquals("http://www.google.co.uk", logFileReader.get(0));
		assertEquals("http://www.theguardian.com",
				logFileReader.get(logFileReader.getSize() - 1));
	}

	@Test
	public void shouldReturnUrlsAsListOfRequests() throws BadLogFileException,
			IOException {
		File logFile = new File(getClass().getClassLoader()
				.getResource("urls.log").getFile());
		LogFileReader logFileReader = new LogFileReader(logFile);
		List<Request> asListOfUrls = logFileReader.getAsListOfUrls();
		assertEquals(6, asListOfUrls.size());
	}

	@Test(expected = IOException.class)
	public void shouldErrorOnMalformedUrlInLogFile() throws BadLogFileException {
		File logFile = new File(getClass().getClassLoader()
				.getResource("urlsWithMalformedUrl.log").getFile());
		LogFileReader logFileReader = new LogFileReader(logFile);
		logFileReader.getAsListOfUrls();
	}

}

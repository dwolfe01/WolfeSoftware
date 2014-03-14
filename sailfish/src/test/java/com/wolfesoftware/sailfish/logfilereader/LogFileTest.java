package com.wolfesoftware.sailfish.logfilereader;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;

//TODO: make this an integration test?
public class LogFileTest {

	private LogFileReader logFileReader;

	@Before
	public void setup() throws IOException {
		File logFile = new File(getClass().getClassLoader()
				.getResource("urls.log").getFile());
		logFileReader = new LogFileReader(logFile);
	}

	@Test
	public void shouldReadLogFileAndOutputSize() throws Exception {
		assertEquals(6, logFileReader.getSize());
	}

	@Test
	public void shouldReadFirstAndLastStringFromLogFile() throws Exception {
		assertEquals("http://www.google.co.uk", logFileReader.get(0));
		assertEquals("http://www.theguardian.com",
				logFileReader.get(logFileReader.getSize() - 1));
	}

	@Test
	public void shouldReturnListIterator() throws Exception {
		logFileReader.iterator();
	}

}

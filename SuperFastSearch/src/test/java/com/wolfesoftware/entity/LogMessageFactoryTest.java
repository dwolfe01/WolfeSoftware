package com.wolfesoftware.entity;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

public class LogMessageFactoryTest {
	
	private LogMessageFactory logMessageFactory = new LogMessageFactory();
	
	@Test
	public void shouldGetLogFileEntityFromString() throws ParseException, IOException {
		LogMessage logMessage = logMessageFactory.getLogMessage("194.247.11.63 [20/03/2019:16:03:00 +0000] /testURL31/4444xyz/extend6");
		assertEquals("194.247.11.63",logMessage.getIP());
		assertEquals("/testURL31/4444xyz/extend6",logMessage.getRequest());
		assertEquals("Wed Mar 20 16:03:00 GMT 2019",logMessage.getDate().toString());
	}

	@Test
	public void shouldGetLogFileEntityFromLogFile() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_short.log");
		List<LogMessage> logMessagesFromLogFile = logMessageFactory.getLogMessagesFromLogFile(logFile);
		assertEquals(10,logMessagesFromLogFile.size());
		LogMessage logMessage = logMessagesFromLogFile.get(0);
		assertEquals("125.8.33.37",logMessage.getIP());
		assertEquals("/testURL11/4444xyz",logMessage.getRequest());
		assertEquals("Mon Mar 20 16:03:00 GMT 2017",logMessage.getDate().toString());
		logMessage = logMessagesFromLogFile.get(5);
		assertEquals("194.182.31.54",logMessage.getIP());
		assertEquals("/testURL31/4444xyz/extend6",logMessage.getRequest());
		assertEquals("Mon Mar 20 16:03:00 GMT 2017",logMessage.getDate().toString());
		logFile.close();
	}
	
	@Test
	public void shouldGetLogFileEntityFromLogFileForGivenIP() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		List<LogMessage> logMessagesFromLogFile = logMessageFactory.getLogMessagesFromLogFileForIP(logFile,"193.30.27.36");
		assertEquals(17, logMessagesFromLogFile.size());
		logMessageFactory.prettyPrint(logMessagesFromLogFile);
		logFile.close();
	}
	
}

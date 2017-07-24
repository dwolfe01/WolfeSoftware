package com.wolfesoftware.entity;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.wolfesoftware.entity.LogMessageFactory.FIELD;

public class LogMessageFactoryTest {
	
	private List<FIELD> ordering = Arrays.asList(LogMessageFactory.FIELD.IP, LogMessageFactory.FIELD.DATE, LogMessageFactory.FIELD.REQUEST);
	private LogMessageFactory logMessageFactory = new LogMessageFactory("(\\S+)\\s\\[(.*)\\]\\s(.*)", "dd/MM/yyyy:HH:mm:ss Z", ordering);
	
	@Test
	public void shouldGetLogFileEntityFromString() throws ParseException, IOException {
		LogMessage logMessage = logMessageFactory.getLogMessage("194.247.11.63 [20/03/2019:16:03:00 +0000] /testURL31/4444xyz/extend6");
		assertEquals("194.247.11.63",logMessage.getIP());
		assertEquals("/testURL31/4444xyz/extend6",logMessage.getRequest());
		assertEquals("2019-03-20T16:03",logMessage.getDate().toString());
	}

	@Test
	public void shouldGetLogFileEntityFromStringDifferentOrdering() throws ParseException, IOException {
		List<FIELD> ordering = Arrays.asList(LogMessageFactory.FIELD.DATE, LogMessageFactory.FIELD.IP, LogMessageFactory.FIELD.REQUEST);
		logMessageFactory = new LogMessageFactory("\\[(.*)\\]\\s(\\S+)\\s(.*)", "dd/MM/yyyy:HH:mm:ss Z", ordering);
		LogMessage logMessage = logMessageFactory.getLogMessage("[20/03/2019:16:03:00 +0000] 194.247.11.63 /testURL31/4444xyz/extend6");
		assertEquals("194.247.11.63",logMessage.getIP());
		assertEquals("/testURL31/4444xyz/extend6",logMessage.getRequest());
		assertEquals("2019-03-20T16:03",logMessage.getDate().toString());
	}

	@Test
	public void shouldGetLogFileEntityFromStringDifferentOrderingAsString() throws ParseException, IOException {
		logMessageFactory = new LogMessageFactory("\\[(.*)\\]\\s(\\S+)\\s(.*)", "dd/MM/yyyy:HH:mm:ss Z", "DATE,IP,REQUEST");
		LogMessage logMessage = logMessageFactory.getLogMessage("[20/03/2019:16:03:00 +0000] 194.247.11.63 /testURL31/4444xyz/extend6");
		assertEquals("194.247.11.63",logMessage.getIP());
		assertEquals("/testURL31/4444xyz/extend6",logMessage.getRequest());
		assertEquals("2019-03-20T16:03",logMessage.getDate().toString());
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldGetLogFileEntityFromStringDifferentOrderingAsStringShouldThrowRunTime() throws ParseException, IOException {
		logMessageFactory = new LogMessageFactory("\\[(.*)\\]\\s(\\S+)\\s(.*)", "dd/MM/yyyy:HH:mm:ss Z", "DATE,IP,REQUEST2");
	}

	@Test
	public void shouldGetLogFileEntityFromLogFile() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_short.log");
		List<LogMessage> logMessagesFromLogFile = logMessageFactory.getLogMessagesFromLogFile(logFile);
		assertEquals(10,logMessagesFromLogFile.size());
		LogMessage logMessage = logMessagesFromLogFile.get(0);
		assertEquals("125.8.33.37",logMessage.getIP());
		assertEquals("/testURL11/4444xyz",logMessage.getRequest());
		assertEquals("2017-03-20T16:03",logMessage.getDate().toString());
		logMessage = logMessagesFromLogFile.get(5);
		assertEquals("194.182.31.54",logMessage.getIP());
		assertEquals("/testURL31/4444xyz/extend6",logMessage.getRequest());
		assertEquals("2017-03-20T16:03",logMessage.getDate().toString());
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
	
	@Test
	public void shouldGetLogFileEntityFromLogFileForAnyGivenLambda() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		List<LogMessage> logMessagesFromLogFile = logMessageFactory.getLogMessagesFromLogFileForAnyGivenLambda(logFile,logMessage -> logMessage.getIP().equals("193.30.27.36"));
		assertEquals(17, logMessagesFromLogFile.size());
		logMessageFactory.prettyPrint(logMessagesFromLogFile);
		logFile.close();
	}
	
}

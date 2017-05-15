package com.wolfesoftware.entity;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.wolfesoftware.stats.LogFileStats;

public class LogMessageFactoryTest {
	
	private LogMessageFactory logMessageFactory = new LogMessageFactory();

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
	}
	
	@Test
	public void shouldProcessLogFileStatsAroundLogFile() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		LogFileStats lfs = logMessageFactory.getLogFileStats(logFile);
		assertEquals(1000,lfs.getNumberOfLogMessages());
	}
	
	@Test
	public void getTop10IPAddresses() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		LogFileStats lfs = logMessageFactory.getLogFileStats(logFile);
		Map<String, Integer> topIP = lfs.topIP(10);
		lfs.prettyPrint();
	}
	
}

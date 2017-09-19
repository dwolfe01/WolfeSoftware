package com.wolfesoftware.stats;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;

import org.junit.Test;

public class LogFileStatsTest {
	
	LogFileStats lfs; 
	
	@Test
	public void shouldProcessLogFileStatsAroundLogFile() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		lfs = new LogFileStats(logFile,"(\\S+)\\s\\[(.*)\\]\\s(.*)", "dd/MM/yyyy:HH:mm:ss Z", "IP,DATE,REQUEST");
		logFile.close();
		assertEquals(1000,lfs.getNumberOfLogMessages());
	}

	@Test
	public void shouldIgnoreUnParseableLogMessage() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_short_with_bad_log_message.log");
		lfs = new LogFileStats(logFile,"(\\S+)\\s\\[(.*)\\]\\s(.*)", "dd/MM/yyyy:HH:mm:ss Z", "IP,DATE,REQUEST");
		logFile.close();
		assertEquals(9,lfs.getNumberOfLogMessages());
	}
	
	@Test
	public void getOffendingIPAddresses() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		lfs = new LogFileStats(logFile,"(\\S+)\\s\\[(.*)\\]\\s(.*)", "dd/MM/yyyy:HH:mm:ss Z", "IP,DATE,REQUEST");
		logFile.close();
		Map<String, Integer> ipCounts = lfs.getIPsWithXNumberOfHits(16);
		lfs.prettyPrint();
		assertEquals(13, ipCounts.size());
		assertEquals(true, ipCounts.containsKey("117.184.3.34"));
		assertEquals(true, ipCounts.containsKey("135.94.0.73"));
		assertEquals(true, ipCounts.containsKey("193.30.27.36"));
		assertEquals(true, ipCounts.containsKey("75.67.36.45"));
		assertEquals(false, ipCounts.containsKey("76.80.27.75"));
	}
	
	@Test
	public void getMostPopularRequests() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		lfs = new LogFileStats(logFile,"(\\S+)\\s\\[(.*)\\]\\s(.*)", "dd/MM/yyyy:HH:mm:ss Z", "IP,DATE,REQUEST");
		logFile.close();
		Map<String, Integer> popularURLS = lfs.getMostPopularRequests(10);
		assertEquals(10, popularURLS.size());
		assertEquals(true, popularURLS.containsKey("/testURL2/4444xyz.html"));
		assertEquals(false, popularURLS.containsKey("/SuperFastSearch?requestParam=moon;&requestParam=enceladus"));
	}
	
}

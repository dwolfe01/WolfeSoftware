package com.wolfesoftware.stats;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wolfesoftware.entity.LogMessage;
import com.wolfesoftware.stats.LogFileStats;

public class LogFileStatsTest {
	
	LogFileStats lfs; 
	
	@Before
	public void before() throws ParseException, IOException{
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		lfs = new LogFileStats(logFile);
		logFile.close();
	}
	
	@Test
	public void shouldProcessLogFileStatsAroundLogFile() throws ParseException, IOException {
		assertEquals(1000,lfs.getNumberOfLogMessages());
	}
	
	@Test
	public void getOffendingIPAddresses() throws ParseException, IOException {
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
		Map<String, Integer> popularURLS = lfs.getMostPopularRequests(10);
		assertEquals(10, popularURLS.size());
		assertEquals(true, popularURLS.containsKey("/testURL2/4444xyz.html"));
		assertEquals(false, popularURLS.containsKey("/SuperFastSearch?requestParam=moon;&requestParam=enceladus"));
	}
	
}

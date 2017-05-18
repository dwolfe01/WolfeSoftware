package com.wolfesoftware.stats;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.wolfesoftware.stats.LogFileStats;

public class LogFileStatsTest {
	
	@Test
	public void shouldProcessLogFileStatsAroundLogFile() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		LogFileStats lfs = new LogFileStats(logFile);
		assertEquals(1000,lfs.getNumberOfLogMessages());
	}
	
	@Test
	public void getOffendingIPAddresses() throws ParseException, IOException {
		InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("apache_medium.log");
		LogFileStats lfs = new LogFileStats(logFile);
		Map<String, Integer> ipCounts = lfs.getIPsWithXNumberOfHits(30);
		assertEquals(13, ipCounts.size());
		assertEquals(true, ipCounts.containsKey("117.184.3.34"));
		assertEquals(true, ipCounts.containsKey("135.94.0.73"));
		assertEquals(true, ipCounts.containsKey("193.30.27.36"));
		assertEquals(true, ipCounts.containsKey("75.67.36.45"));
		assertEquals(false, ipCounts.containsKey("135.34.72"));
	}
	
}

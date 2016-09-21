package com.wolfesoftware.sailfish.http.uptime;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class UptimeHistoryTest {

	@Test
	public void shouldMaintainUpTimeHistoryForGivenURL(){
		UptimeHistory uptimeHistory = new UptimeHistory();
		uptimeHistory.update("test1",300L);
		assertEquals(new Long(300L), uptimeHistory.getMilliseconds("test1"));
		assertEquals(new Double(300),uptimeHistory.getAverageResponseTime("test1"));
	}
	
	@Test
	public void shouldMaintainUpTimeHistoryForGivenURLs(){
		UptimeHistory uptimeHistory = new UptimeHistory();
		uptimeHistory.update("test1",300L);
		uptimeHistory.update("test1",300L);
		uptimeHistory.update("test2",300L);
		assertEquals(new Long(600L), uptimeHistory.getMilliseconds("test1"));
		assertEquals(new Long(300L), uptimeHistory.getMilliseconds("test2"));
		assertEquals(new Double(300),uptimeHistory.getAverageResponseTime("test1"));
		assertEquals(new Double(300),uptimeHistory.getAverageResponseTime("test1"));
	}
	
	@Test
	public void shouldPrettyPrint(){
		UptimeHistory uptimeHistory = new UptimeHistory();
		uptimeHistory.update("test1",300L);
		uptimeHistory.update("test1",300L);
		uptimeHistory.update("test2",300L);
		System.out.println(uptimeHistory.prettyPrint());
		assertEquals(true, uptimeHistory.prettyPrint().contains("url:test1 count:2 average:300.0 milliseconds"));
		assertEquals(true, uptimeHistory.prettyPrint().contains("url:test2 count:1 average:300.0 milliseconds"));
	}
	
}

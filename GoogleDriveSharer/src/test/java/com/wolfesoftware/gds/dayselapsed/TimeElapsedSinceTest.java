package com.wolfesoftware.gds.dayselapsed;

import java.time.LocalDateTime;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TimeElapsedSinceTest {
	
	@Test
	public void testDays() throws Exception {
		LocalDateTime begin = LocalDateTime.parse("2015-02-20T06:30:00");
		LocalDateTime end = LocalDateTime.parse("2015-02-21T06:30:00");
		assertEquals("1 day(s)", new TimeElapsedSince().timeBetween(begin, end));
	}
	
	@Test
	public void testYears() throws Exception {
		LocalDateTime begin = LocalDateTime.parse("2015-02-20T06:30:00");
		LocalDateTime end = LocalDateTime.parse("2016-02-20T06:30:00");
		assertEquals("1 year(s) ", new TimeElapsedSince().timeBetween(begin, end));
	}

	@Test
	public void testMonths() throws Exception {
		LocalDateTime begin = LocalDateTime.parse("2015-02-20T06:30:00");
		LocalDateTime end = LocalDateTime.parse("2015-03-20T06:30:00");
		assertEquals("1 month(s) ", new TimeElapsedSince().timeBetween(begin, end));
	}

	@Test
	public void testYearsMonthsDays() throws Exception {
		LocalDateTime begin = LocalDateTime.parse("2015-02-20T06:30:00");
		LocalDateTime end = LocalDateTime.parse("2016-03-30T06:30:00");
		assertEquals("1 year(s) 1 month(s) 10 day(s)", new TimeElapsedSince().timeBetween(begin, end));
	}

}

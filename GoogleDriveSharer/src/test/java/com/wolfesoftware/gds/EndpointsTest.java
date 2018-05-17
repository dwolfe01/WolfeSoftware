package com.wolfesoftware.gds;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EndpointsTest {
	
	@Test
	public void shouldCreateDateTimeFormatter() {
		Endpoints endpoints = new Endpoints();
		assertEquals("Wednesday, May 16, 2018", endpoints.formatDate("2018-05-16T20:43:38.794Z"));
	}

}

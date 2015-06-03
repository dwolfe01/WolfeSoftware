package com.wolfesoftware.sailfish.runnable.httpuser;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.wolfesoftware.sailfish.requests.SimpleRequest;

@Ignore
public class SimpleRequestTest {
	
	@Test
	public void shouldEncodeUrl() throws Exception {
		String uri = "http://www.bad+request.com";
		String expected = "http://www.bad%23request.com";
		SimpleRequest simpleRequest = new SimpleRequest(uri);
		assertEquals(expected, simpleRequest.getUri());
	}

}

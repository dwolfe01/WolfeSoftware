package com.wolfesoftware.sailfish.worker.httpuser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.request.Request;

public class HttpUserTest {

	HttpUser httpUser;

	@Mock
	Request request;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		httpUser = new HttpUser();
	}

	@Test
	public void shouldEstablishASessionWithTheServer() throws Exception {
		httpUser.establishSession(request).go();
		Mockito.verify(request, Mockito.times(1)).go();
	}

	@Test
	public void shouldExecuteRequestsOneAfterAnother() throws Exception {
		httpUser.add(request).add(request);
		httpUser.go();
		Mockito.verify(request, Mockito.times(2)).go();
	}

	/*
	 * I couldn't really work out how to test Thread.sleep was being called.
	 * This is a bad test.
	 */
	@Test
	public void shouldExecuteRequestsOneAfterAnotherWithWaitTime()
			throws Exception {
		long startTime = System.currentTimeMillis();
		long sleepTime = 50;
		httpUser.setWaitTimeInMilliseconds(sleepTime).add(request).add(request)
				.go();
		Mockito.verify(request, Mockito.times(2)).go();
		long completionTime = System.currentTimeMillis() - startTime;
		assertEquals("Not pausing for long enough", true, completionTime > 100);
	}

}

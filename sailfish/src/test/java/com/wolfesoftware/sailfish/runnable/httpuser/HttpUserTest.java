package com.wolfesoftware.sailfish.runnable.httpuser;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HttpUserTest {

	HttpUser httpUser;

	@Mock
	HttpClient httpClient;
	@Mock
	StatusLine statusLine;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@SuppressWarnings("unchecked")
	// due to difficulties around matchers and generic classes
	public void shouldMakeTwoRequests() throws ClientProtocolException,
			IOException {
		// given
		when(httpClient.execute(isA(HttpGet.class), isA(ResponseHandler.class)))
				.thenReturn(statusLine);
		httpUser = new HttpUser(httpClient);
		httpUser.add("http://www.myurl.com");
		httpUser.add("http://www.myurl2.com");
		// when
		httpUser.run();
		// then
		verify(httpClient, times(2)).execute(isA(HttpUriRequest.class),
				isA(ResponseHandler.class));
	}

	@Test
	@SuppressWarnings("unchecked")
	// due to difficulties around matchers and generic classes
	public void shouldObserveWaitTimeMakeTwoRequests()
			throws ClientProtocolException, IOException {
		// given
		when(httpClient.execute(isA(HttpGet.class), isA(ResponseHandler.class)))
				.thenReturn(statusLine);
		httpUser = new HttpUser(httpClient);
		httpUser.add("http://www.myurl.com");
		httpUser.add("http://www.myurl2.com");
		// when
		long startTime = System.currentTimeMillis();
		httpUser.run();
		long totalExecutionTime = System.currentTimeMillis() - startTime;
		// then
		assertTrue(totalExecutionTime < 20);
		httpUser.setWaitTimeInMilliseconds(50);
		startTime = System.currentTimeMillis();
		httpUser.run();
		totalExecutionTime = System.currentTimeMillis() - startTime;
		assertTrue(totalExecutionTime > 50);
	}

	// @Test(expected = MalformedUrlException.class)
	// public void shouldThrowAnExceptionIfTheRequestIsNotAURL() throws
	// Exception {
	//
	// }

}

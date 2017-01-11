package com.wolfesoftware.sailfish.http.runnable.httpuser;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.http.requests.GetRequest;
import com.wolfesoftware.sailfish.http.requests.PostRequest;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;

public class HttpUserTest {

	HttpUser httpUser;

	@Mock
	CloseableHttpClient httpClient;
	@Mock
	GetRequest getRequest;
	@Mock
	PostRequest postRequest;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	// due to difficulties around matchers and generic classes
	public void shouldMakeTwoRequests() throws ClientProtocolException, IOException, URISyntaxException {
		// given
		ResponseHandlerFactory responseHandlerFactory = new ResponseHandlerFactory();
		httpUser = new HttpUser(httpClient, responseHandlerFactory);
		httpUser.addGetRequest(getRequest);
		httpUser.addGetRequest(getRequest);
		// when
		httpUser.run();
		// then
		verify(getRequest, times(2)).makeRequest(httpClient,responseHandlerFactory);
	}

	@Test
	public void shouldMakePOSTRequest() throws ClientProtocolException, IOException {
		// given
		ResponseHandlerFactory responseHandlerFactory = new ResponseHandlerFactory();
		httpUser = new HttpUser(httpClient, responseHandlerFactory);
		httpUser.addPostRequest(postRequest);
		// when
		httpUser.run();
		// then
		verify(postRequest, times(1)).makeRequest(httpClient, responseHandlerFactory);
	}

	@Test
	// due to difficulties around matchers and generic classes
	public void shouldObserveWaitTimeMakeTwoRequests() throws ClientProtocolException, IOException, URISyntaxException {
		// given
		httpUser = new HttpUser(httpClient, new ResponseHandlerFactory());
		httpUser.addGetRequest(getRequest);
		httpUser.addGetRequest(getRequest);
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

	@Test
	// due to difficulties around matchers and generic classes
	public void shouldMakeNoRequestsIfURLsAreEmptyString() throws ClientProtocolException, IOException {
		// given
		ResponseHandlerFactory responseHandlerFactory = new ResponseHandlerFactory();
		httpUser = new HttpUser(httpClient, responseHandlerFactory);
		httpUser.run();
		// then
		ResponseHandler<StatusLine> instanceOfResponseHandler = responseHandlerFactory.getInstanceOfResponseHandler();
		verify(httpClient, never()).execute(isA(HttpUriRequest.class), isA(instanceOfResponseHandler.getClass()));
	}

}

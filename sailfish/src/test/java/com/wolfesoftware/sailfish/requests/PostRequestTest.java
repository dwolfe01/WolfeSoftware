package com.wolfesoftware.sailfish.requests;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.responsehandler.factory.QuickCloseResponseHandler;
import com.wolfesoftware.sailfish.responsehandler.factory.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.responsehandler.factory.ResponseHandlerFactory.ResponseHandlers;

public class PostRequestTest {

	@Mock
	HttpClient httpClient;
	@Mock
	StatusLine statusLine;
	Class<? extends ResponseHandler<StatusLine>> responseHandler = QuickCloseResponseHandler.class;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldMakeAGetRequest() throws Exception {
		// given
		ResponseHandlerFactory.setHandler(ResponseHandlers.QUICKCLOSE);
		when(httpClient.execute(isA(HttpPost.class), isA(responseHandler))).thenReturn(statusLine);
		new PostRequest("http://www.some-url.com").makeRequest(httpClient);
		verify(httpClient, times(1)).execute(isA(HttpPost.class), isA(responseHandler));
	}

	@Test(expected = MalformedURLException.class)
	public void shouldThrowAnExceptionIfTheRequestIsNotAURL() throws Exception {
		new GetRequest("bad url");
	}

}

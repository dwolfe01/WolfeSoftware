package com.wolfesoftware.sailfish.requests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.responsehandler.QuickCloseResponseHandler;
import com.wolfesoftware.sailfish.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.responsehandler.ResponseHandlerFactory.ResponseHandlers;

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
	public void shouldMakeAPostRequest() throws Exception {
		// given
		ResponseHandlerFactory.setHandler(ResponseHandlers.QUICKCLOSE);
		when(httpClient.execute(isA(HttpPost.class), isA(responseHandler))).thenReturn(statusLine);
		new PostRequest("http://www.some-url.com").makeRequest(httpClient);
		verify(httpClient, times(1)).execute(isA(HttpPost.class), isA(responseHandler));
	}

	@Test
	public void shouldAddNameValuePairsToBeSubmitted() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlers.QUICKCLOSE);
		when(httpClient.execute(isA(HttpPost.class), isA(responseHandler))).thenReturn(statusLine);
		PostRequest postRequest = new PostRequest("http://www.some-url.com");
		postRequest.addNameValuePostPair("username", "user");
		postRequest.addNameValuePostPair("password", "password");
		HttpPost httpPost = postRequest.build();
		UrlEncodedFormEntity entity = (UrlEncodedFormEntity) httpPost.getEntity();
		assertEquals("username=user&password=password", EntityUtils.toString(entity, "UTF-8"));
	}
	
	@Test
	public void deletemeshouldAddNameValuePairsToBeSubmitted() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlers.SYSTEMOUT);
		PostRequest postRequest = new PostRequest("http://10.6.2.197:9197/cache-purger");
		postRequest.addNameValuePostPair("article", "Article");
		postRequest.addNameValuePostPair("text", "test");
		HttpPost httpPost = postRequest.build();
		UrlEncodedFormEntity entity = (UrlEncodedFormEntity) httpPost.getEntity();
		System.out.println(EntityUtils.toString(entity));
	}
	
}

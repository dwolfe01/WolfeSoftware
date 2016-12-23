package com.wolfesoftware.sailfish.http.runnable.httpuser;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.uptime.UptimeHistory;

public class UptimeHttpUserTest extends HttpUserTest{

	HttpUser httpUser;
	@Mock
	UptimeHistory uptimeHistory;
	@Mock
	StatusLine statusLine;
	URI uri;
	
	public UptimeHttpUserTest() throws URISyntaxException{
		uri = new URI("http://test.com");
	}

	@Test
	public void shouldUpdateCentralDocumentWithStatusCodeOfRequest() throws ClientProtocolException, IOException, URISyntaxException {
		// given
		httpUser = new UptimeHttpUser(httpClient, uptimeHistory, new ResponseHandlerFactory());
		httpUser.setWaitTimeInMilliseconds(1);
		httpUser.addGetRequest(getRequest);
		httpUser.addGetRequest(getRequest);
		Mockito.when(getRequest.makeRequest(httpClient)).thenReturn(statusLine);
		Mockito.when(statusLine.getStatusCode()).thenReturn(200);
		Mockito.when(getRequest.getUri()).thenReturn(uri);
		// when
		httpUser.run();
		// then
		verify(uptimeHistory, times(2)).update(isA(String.class),isA(Long.class));
	}

	@Test
	public void shouldUpdateHistoryDocumentInTheEventOfAnException() throws ClientProtocolException, IOException, URISyntaxException {
		httpUser = new UptimeHttpUser(httpClient, uptimeHistory, new ResponseHandlerFactory());
		httpUser.setWaitTimeInMilliseconds(1);
		httpUser.addGetRequest(getRequest);
		Mockito.when(getRequest.makeRequest(httpClient)).thenThrow(new RuntimeException());
		Mockito.when(getRequest.getUri()).thenReturn(uri);
		// when
		httpUser.run();
		// then
		verify(uptimeHistory, times(1)).update(isA(String.class),isA(Long.class));
	}
	
}

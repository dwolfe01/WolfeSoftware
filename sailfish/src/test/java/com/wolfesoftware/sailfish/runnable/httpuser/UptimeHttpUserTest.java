package com.wolfesoftware.sailfish.runnable.httpuser;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.apache.http.StatusLine;

import static org.mockito.Matchers.isA;

import com.wolfesoftware.sailfish.uptime.UptimeHistory;

public class UptimeHttpUserTest extends HttpUserTest{

	UptimeHttpUser httpUser;
	@Mock
	UptimeHistory uptimeHistory;
	@Mock
	StatusLine statusLine;

	@Test
	public void shouldUpdateCentralDocumentWithStatusCodeOfRequest() throws ClientProtocolException, IOException {
		// given
		httpUser = new UptimeHttpUser(httpClient, uptimeHistory);
		httpUser.addGetRequest(getRequest);
		httpUser.addGetRequest(getRequest);
		Mockito.when(getRequest.makeRequest(httpClient)).thenReturn(statusLine);
		Mockito.when(statusLine.getStatusCode()).thenReturn(200);
		// when
		httpUser.run();
		// then
		verify(uptimeHistory, times(2)).update(isA(String.class),isA(Long.class));
	}

	@Test
	public void shouldUpdateHistoryDocumentInTheEventOfAnException() throws ClientProtocolException, IOException {
		httpUser = new UptimeHttpUser(httpClient, uptimeHistory);
		httpUser.addGetRequest(getRequest);
		Mockito.when(getRequest.makeRequest(httpClient)).thenThrow(new RuntimeException());
		// when
		httpUser.run();
		// then
		verify(uptimeHistory, times(1)).update(isA(String.class),isA(Long.class));
	}


}

package com.wolfesoftware.sailfish.http.responsehandler.factory;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.OutputStream;
import java.util.Collections;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.http.responsehandler.PrintHeadersResponseHandler;

public class PrintHeadersResponseHandlerTest {

	@Mock
	HttpResponse response;
	@Mock
	StringEntity stringEntity;
	@Mock
	OutputStream os;
	@Mock
	Header header;
	@Mock
	Header anotherHeader;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldWriteHeaderToOutputStream() throws Exception {
		// given
		PrintHeadersResponseHandler responseHandler = new PrintHeadersResponseHandler(
				os);
		Mockito.when(header.getName()).thenReturn("test");
		Mockito.when(header.getValue()).thenReturn("ergonomics");
		Header[] headers = new Header[1];
		headers[0]=header;
		Mockito.when(response.getAllHeaders()).thenReturn(headers);
		// when
		responseHandler.handleResponse(response);
		// then
		verify(response, times(1)).getStatusLine();
		verify(os, times(1)).write("test:ergonomics".getBytes());
	}
	
	@Test
	public void shouldWriteHeadersToOutputStream() throws Exception {
		// given
		PrintHeadersResponseHandler responseHandler = new PrintHeadersResponseHandler(
				os);
		Mockito.when(header.getName()).thenReturn("test");
		Mockito.when(header.getValue()).thenReturn("ergonomics");
		Mockito.when(anotherHeader.getName()).thenReturn("cheese");
		Mockito.when(anotherHeader.getValue()).thenReturn("pickle");
		Header[] headers = new Header[2];
		headers[0]=header;
		headers[1]=anotherHeader;
		Mockito.when(response.getAllHeaders()).thenReturn(headers);
		// when
		responseHandler.handleResponse(response);
		// then
		verify(response, times(1)).getStatusLine();
		verify(os, times(1)).write("test:ergonomics".getBytes());
		verify(os, times(1)).write("cheese:pickle".getBytes());
	}
	
}

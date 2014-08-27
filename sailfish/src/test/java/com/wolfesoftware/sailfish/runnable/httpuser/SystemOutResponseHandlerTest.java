package com.wolfesoftware.sailfish.runnable.httpuser;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SystemOutResponseHandlerTest {

	@Mock
	HttpResponse response;
	@Mock
	StringEntity stringEntity;
	@Mock
	OutputStream os;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldWriteToOutputStream() throws Exception {
		// given
		SystemOutResponseHandler responseHandler = new SystemOutResponseHandler(
				os);
		Mockito.when(response.getEntity()).thenReturn(stringEntity);
		// when
		responseHandler.handleResponse(response);
		// then
		verify(response, times(1)).getStatusLine();
		verify(stringEntity, times(1)).writeTo(os);
	}
}

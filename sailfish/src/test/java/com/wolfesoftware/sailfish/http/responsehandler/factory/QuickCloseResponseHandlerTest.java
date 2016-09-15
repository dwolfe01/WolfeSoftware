package com.wolfesoftware.sailfish.http.responsehandler.factory;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.http.responsehandler.QuickCloseResponseHandler;

public class QuickCloseResponseHandlerTest {

	@Mock
	HttpResponse response;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldOnlyGetStatusLineANDNotGetTheContent() throws Exception {
		QuickCloseResponseHandler responseHandler = new QuickCloseResponseHandler();
		responseHandler.handleResponse(response);
		verify(response, never()).getEntity();
		verify(response, times(1)).getStatusLine();
	}
}

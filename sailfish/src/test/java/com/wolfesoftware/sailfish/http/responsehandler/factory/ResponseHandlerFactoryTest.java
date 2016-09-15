package com.wolfesoftware.sailfish.http.responsehandler.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.http.responsehandler.OutputStreamResponseHandler;
import com.wolfesoftware.sailfish.http.responsehandler.QuickCloseResponseHandler;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.responsehandler.SystemOutResponseHandler;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory.ResponseHandlers;

public class ResponseHandlerFactoryTest {

	@Mock
	OutputStream os;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateResponseHandlersOfTypeQuickCloseResponseHandler() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlers.DEFAULT);
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory.getInstanceOfResponseHandler();
		assertTrue(responseHandler instanceof QuickCloseResponseHandler);
	}

	@Test
	public void shouldCreateResponseHandlersOfTypeSystemOutResponseHandler() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlers.SYSTEMOUT);
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory.getInstanceOfResponseHandler();
		assertTrue(responseHandler instanceof SystemOutResponseHandler);
	}

	@Test
	public void shouldCreateOutputStreamHandlerWithTheCorrectOutputSream() {
		ResponseHandlerFactory.setOutputStream(os);
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory.getInstanceOfResponseHandler(ResponseHandlers.OUTPUTSTREAM);
		assertTrue(responseHandler instanceof OutputStreamResponseHandler);
		assertEquals(os, ((OutputStreamResponseHandler)responseHandler).getOs());
	}

}

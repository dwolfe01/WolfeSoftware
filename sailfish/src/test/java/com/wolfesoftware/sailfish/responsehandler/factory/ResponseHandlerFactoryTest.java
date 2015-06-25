package com.wolfesoftware.sailfish.responsehandler.factory;

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

public class ResponseHandlerFactoryTest {

	@Mock
	OutputStream os;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateResponseHandlersOfTypeQuickCloseResponseHandler() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlerFactory.ResponseHandlers.DEFAULT);
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory.getInstanceOfResponseHandler();
		assertTrue(responseHandler instanceof QuickCloseResponseHandler);
	}

	@Test
	public void shouldCreateResponseHandlersOfTypeSystemOutResponseHandler() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlerFactory.ResponseHandlers.SYSTEMOUT);
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory.getInstanceOfResponseHandler();
		assertTrue(responseHandler instanceof SystemOutResponseHandler);
	}

	@Test
	public void shouldCreateResponseHandlersOfTypeSystemOutResponseHandlerIfOutputStreamNotSet() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlerFactory.ResponseHandlers.OUTPUTSTREAM);
		ResponseHandlerFactory.ResponseHandlers.OUTPUTSTREAM.setOutputStream(null);
		// set output stream here
		SystemOutResponseHandler responseHandler = (SystemOutResponseHandler) ResponseHandlerFactory.getInstanceOfResponseHandler();
		assertTrue(responseHandler instanceof SystemOutResponseHandler);
	}

	@Test
	public void shouldCreateResponseHandlersOfTypeOutputStreamResponseHandlerIfOutputStreamISSet() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlerFactory.ResponseHandlers.OUTPUTSTREAM);
		ResponseHandlerFactory.ResponseHandlers.OUTPUTSTREAM.setOutputStream(os);
		// set output stream here
		OutputStreamResponseHandler responseHandler = (OutputStreamResponseHandler) ResponseHandlerFactory.getInstanceOfResponseHandler();
		assertEquals(responseHandler.getOs(), os);
	}
	
	@Test
	public void shouldCreateResponseHandlersOfTypePrintHeadersResponseHandlerIfOutputStreamISSet() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlerFactory.ResponseHandlers.PRINTHEADERS);
		ResponseHandlerFactory.ResponseHandlers.PRINTHEADERS.setOutputStream(os);
		// set output stream here
		PrintHeadersResponseHandler responseHandler = (PrintHeadersResponseHandler) ResponseHandlerFactory.getInstanceOfResponseHandler();
		assertEquals(responseHandler.getOs(), os);
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionIfNoOutputStreamSetonPrintHeadersResponseHandler() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlerFactory.ResponseHandlers.PRINTHEADERS);
		ResponseHandlerFactory.getInstanceOfResponseHandler();
	}

	@Test(expected = NoSuchMethodException.class)
	public void shouldThrowExceptionIfSetOutputStreamIsCalledOnQuickCloseResponseHandler() throws Exception {
		ResponseHandlerFactory.setHandler(ResponseHandlerFactory.ResponseHandlers.QUICKCLOSE);
		ResponseHandlerFactory.ResponseHandlers.QUICKCLOSE.setOutputStream(os);
	}

}

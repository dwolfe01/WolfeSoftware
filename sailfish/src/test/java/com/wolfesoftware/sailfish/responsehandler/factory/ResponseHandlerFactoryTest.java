package com.wolfesoftware.sailfish.responsehandler.factory;

import static org.junit.Assert.assertTrue;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.junit.Test;

import com.wolfesoftware.sailfish.runnable.httpuser.QuickCloseResponseHandler;
import com.wolfesoftware.sailfish.runnable.httpuser.SystemOutResponseHandler;

public class ResponseHandlerFactoryTest {

	@Test
	public void shouldCreateResponseHandlersOfTypeQuickCloseResponseHandler()
			throws Exception {
		ResponseHandlerFactory
				.setHandler(ResponseHandlerFactory.ResponseHandlers.DEFAULT);
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory
				.getInstanceOfResponseHandler();
		assertTrue(responseHandler instanceof QuickCloseResponseHandler);
	}

	@Test
	public void shouldCreateResponseHandlersOfTypeSystemOutResponseHandler()
			throws Exception {
		ResponseHandlerFactory
				.setHandler(ResponseHandlerFactory.ResponseHandlers.SYSTEMOUT);
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory
				.getInstanceOfResponseHandler();
		assertTrue(responseHandler instanceof SystemOutResponseHandler);
	}
}

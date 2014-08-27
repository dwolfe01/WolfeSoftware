package com.wolfesoftware.sailfish.responsehandler.factory;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;

import com.wolfesoftware.sailfish.runnable.httpuser.QuickCloseResponseHandler;
import com.wolfesoftware.sailfish.runnable.httpuser.SystemOutResponseHandler;

public class ResponseHandlerFactory {

	private String handler = "";

	public ResponseHandlerFactory(String handler) {
		this.handler = handler;
	}

	public ResponseHandlerFactory() {
	}

	public ResponseHandler<StatusLine> getInstanceOfResponseHandler() {
		if (handler.equals("SystemOut")) {
			return new SystemOutResponseHandler();
		}
		return new QuickCloseResponseHandler();
	}
}

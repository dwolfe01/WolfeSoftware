package com.wolfesoftware.sailfish.responsehandler.factory;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;

import com.wolfesoftware.sailfish.runnable.httpuser.QuickCloseResponseHandler;
import com.wolfesoftware.sailfish.runnable.httpuser.SystemOutResponseHandler;

public class ResponseHandlerFactory {

	public static ResponseHandlers currentResponseHandler = ResponseHandlers.DEFAULT;

	public static void setHandler(ResponseHandlers handler) {
		currentResponseHandler = handler;
	}

	public static ResponseHandler<StatusLine> getInstanceOfResponseHandler() {
		return currentResponseHandler.getNewHandler();
	}

	public enum ResponseHandlers {
		DEFAULT() {
			@Override
			public ResponseHandler<StatusLine> getNewHandler() {
				return new QuickCloseResponseHandler();
			}
		},
		SYSTEMOUT() {
			@Override
			public ResponseHandler<StatusLine> getNewHandler() {
				return new SystemOutResponseHandler();
			}
		},
		QUICKCLOSE() {
			@Override
			public ResponseHandler<StatusLine> getNewHandler() {
				return new QuickCloseResponseHandler();
			}
		};

		public abstract ResponseHandler<StatusLine> getNewHandler();

	};

}

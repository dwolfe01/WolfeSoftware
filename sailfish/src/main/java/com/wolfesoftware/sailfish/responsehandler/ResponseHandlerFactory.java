package com.wolfesoftware.sailfish.responsehandler;

import java.io.OutputStream;

import javax.management.RuntimeErrorException;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;

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
		},
		OUTPUTSTREAM {
			OutputStream os = null;

			public void setOutputStream(OutputStream os) {
				this.os = os;
			}

			@Override
			public ResponseHandler<StatusLine> getNewHandler() {
				if (os == null) {// TODO:throw exception in this case
					return new SystemOutResponseHandler();
				} else {
					return new OutputStreamResponseHandler(os);
				}
			}
		},
		PRINTHEADERS {
			OutputStream os = null;

			public void setOutputStream(OutputStream os) {
				this.os = os;
			}

			@Override
			public ResponseHandler<StatusLine> getNewHandler() {
				if (os == null) {// TODO:throw exception in this case
					throw new RuntimeErrorException(null, "You must provide an ouput stream for this handler");
				} else {
					return new PrintHeadersResponseHandler(os);
				}
			}
		}
		;

		public abstract ResponseHandler<StatusLine> getNewHandler();

		public void setOutputStream(OutputStream os)
				throws NoSuchMethodException {
			throw new NoSuchMethodException();
		};

	};

}

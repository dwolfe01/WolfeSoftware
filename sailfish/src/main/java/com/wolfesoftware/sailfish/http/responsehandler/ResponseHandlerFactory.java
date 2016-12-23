package com.wolfesoftware.sailfish.http.responsehandler;

import java.io.OutputStream;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;

import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory.ResponseHandlers;

public class ResponseHandlerFactory {

	private static ResponseHandlers handler = ResponseHandlers.DEFAULT;

	public enum ResponseHandlers {
		DEFAULT,
		SYSTEMOUT,
		QUICKCLOSE,
		OUTPUTSTREAM,
		PRINTHEADERS ;
	};
	
	public static OutputStream outputStream = System.out;
	
	public static void setOutputStream(OutputStream os){
		outputStream = os;
	}
	
	public static OutputStream getOutputStream(){
		return outputStream;
	}
	
	public static ResponseHandler<StatusLine> getInstanceOfResponseHandler() {
		return getInstanceOfResponseHandler(handler);
	}
	
	public static ResponseHandler<StatusLine> getInstanceOfResponseHandler(ResponseHandlers handler) {
		switch (handler){
		 case DEFAULT:  
			 return new QuickCloseResponseHandler();
		 case SYSTEMOUT:
			 return new OutputStreamResponseHandler();
		 case QUICKCLOSE:
			 return new QuickCloseResponseHandler();
		 case OUTPUTSTREAM:
			 return new OutputStreamResponseHandler(getOutputStream());
		 case PRINTHEADERS:
			 return new PrintHeadersResponseHandler(getOutputStream());
		 default:  
			 return new QuickCloseResponseHandler();
		}
	}

	public static void setHandler(ResponseHandlers handler) {
		ResponseHandlerFactory.handler = handler;
	}
}

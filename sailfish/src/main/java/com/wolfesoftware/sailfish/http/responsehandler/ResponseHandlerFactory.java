package com.wolfesoftware.sailfish.http.responsehandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;

public class ResponseHandlerFactory {
	
	public enum ResponseHandlers {
		DEFAULT,
		SYSTEMOUT,
		QUICKCLOSE,
		OUTPUTSTREAM,
		PRINTHEADERS, SAVETOFILE ;
	};

	private static ResponseHandlers handler = ResponseHandlers.DEFAULT;
	
	
	public ResponseHandlers getHandler() {
		return handler;
	}

	public ResponseHandlerFactory() {
	}

	public static OutputStream outputStream = System.out;
	
	public ResponseHandlerFactory(ResponseHandlers responseHandler) {
		handler = responseHandler;
	}

	public void setOutputStream(OutputStream os){
		outputStream = os;
	}
	
	public OutputStream getOutputStream(){
		return outputStream;
	}
	
	public ResponseHandler<StatusLine> getInstanceOfResponseHandler() {
		return getInstanceOfResponseHandler(handler);
	}
	
	public ResponseHandler<StatusLine> getInstanceOfResponseHandler(ResponseHandlers handler) {
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
		 case SAVETOFILE:
			 return new SaveToFileResponseHandler(new File("/tmp/scratch"));
		 default:  
			 return new QuickCloseResponseHandler();
		}
	}


	public static void setDefaultOutputStreamTESTONLY(OutputStream os) {
		outputStream = os;
	}
	
	public static void setDefaultHandlerTESTONLY(ResponseHandlers responseHandler) {
		handler = responseHandler;
	}
	

}

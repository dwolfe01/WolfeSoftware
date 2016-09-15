package com.wolfesoftware.sailfish.http.responsehandler;

public class SystemOutResponseHandler extends OutputStreamResponseHandler {

	public SystemOutResponseHandler() {
		super(System.out);
	}

}

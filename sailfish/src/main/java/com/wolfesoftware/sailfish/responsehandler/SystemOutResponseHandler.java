package com.wolfesoftware.sailfish.responsehandler;

public class SystemOutResponseHandler extends OutputStreamResponseHandler {

	public SystemOutResponseHandler() {
		super(System.out);
	}

}

package com.wolfesoftware.sailfish.responsehandler.factory;

public class SystemOutResponseHandler extends OutputStreamResponseHandler {

	public SystemOutResponseHandler() {
		super(System.out);
	}

}

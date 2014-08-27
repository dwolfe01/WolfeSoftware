package com.wolfesoftware.sailfish.runnable.httpuser;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class SystemOutResponseHandler implements ResponseHandler<StatusLine> {

	OutputStream os;

	public SystemOutResponseHandler() {
		this.os = System.out;
	}

	public SystemOutResponseHandler(OutputStream os) {
		this.os = os;
	}

	@Override
	public StatusLine handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		response.getEntity().writeTo(os);
		return response.getStatusLine();
	}

}

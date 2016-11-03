package com.wolfesoftware.sailfish.http.responsehandler;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class OutputStreamResponseHandler implements ResponseHandler<StatusLine> {

	OutputStream os = System.out;

	public OutputStreamResponseHandler() {
	}

	public OutputStreamResponseHandler(OutputStream os) {
		this.os = os;
	}

	@Override
	public StatusLine handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		response.getEntity().writeTo(os);
		return response.getStatusLine();
	}
	public OutputStream getOs() {
		return os;
	}
}

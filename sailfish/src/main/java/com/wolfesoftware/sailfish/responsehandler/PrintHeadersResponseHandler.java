package com.wolfesoftware.sailfish.responsehandler;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class PrintHeadersResponseHandler implements ResponseHandler<StatusLine> {

	private OutputStream os;

	public PrintHeadersResponseHandler(OutputStream os) {
		this.os = os;
	}
	
	@Override
	public StatusLine handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		Header[] allHeaders = response.getAllHeaders();
		for (Header header : allHeaders){
			os.write((header.getName() + ":" + header.getValue()).getBytes());
		}
		os.write("\n".getBytes());
		return response.getStatusLine();
	}

	public OutputStream getOs() {
		return os;
	}
}

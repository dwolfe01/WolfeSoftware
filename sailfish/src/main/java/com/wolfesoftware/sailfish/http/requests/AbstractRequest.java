package com.wolfesoftware.sailfish.http.requests;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;

public abstract class AbstractRequest {

	protected URI url;

	public AbstractRequest(String uri) throws URISyntaxException {
			this.url = new URI(uri);
	}

	public URI getUri() {
		return url;
	}

	public void setUri(String request) throws URISyntaxException {
		this.url = new URI(request);
	}

	@Override
	public String toString() {
		return this.getUri().toString();
	}
	
	public abstract StatusLine makeRequest(HttpClient httpClient, ResponseHandlerFactory responseHandlerFactory) throws ClientProtocolException, IOException;
}

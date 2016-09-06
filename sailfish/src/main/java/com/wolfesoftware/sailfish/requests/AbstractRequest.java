package com.wolfesoftware.sailfish.requests;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

public abstract class AbstractRequest {

	private URI url;

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
	
	public abstract StatusLine makeRequest(HttpClient httpClient) throws ClientProtocolException, IOException;
}

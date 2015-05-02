package com.wolfesoftware.sailfish.requests;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SimpleRequest {

	private String uri;

	public SimpleRequest(String uri) throws MalformedURLException {
		if (uri != null && !uri.equals("")) {
			URLEncoder.encode(uri);
			new URL(uri);
			this.uri = uri;
		}
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String request) {
		this.uri = request;
	}

	@Override
	public String toString() {
		return this.getUri();
	}
}

package com.wolfesoftware.sailfish.requests;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

public interface Request {

	public StatusLine makeRequest(HttpClient httpClient) throws ClientProtocolException, IOException;

}

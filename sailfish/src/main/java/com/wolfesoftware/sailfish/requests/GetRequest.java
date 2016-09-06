package com.wolfesoftware.sailfish.requests;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;

import com.wolfesoftware.sailfish.responsehandler.ResponseHandlerFactory;

public class GetRequest extends AbstractRequest {

	public GetRequest(String uri) throws URISyntaxException {
		super(uri);
	}

	@Override
	public StatusLine makeRequest(HttpClient httpClient) throws ClientProtocolException, IOException {
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory.getInstanceOfResponseHandler();
		HttpGet request = new HttpGet(this.getUri());
		return httpClient.execute(request, responseHandler);
	}

}

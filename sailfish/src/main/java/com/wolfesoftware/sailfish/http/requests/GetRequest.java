package com.wolfesoftware.sailfish.http.requests;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;

import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;

public class GetRequest extends AbstractRequest {


	public GetRequest(String uri, ResponseHandlerFactory responseHandlerFactory) throws URISyntaxException {
		super(uri, responseHandlerFactory);
	}

	@Override
	public StatusLine makeRequest(HttpClient httpClient) throws ClientProtocolException, IOException {
		ResponseHandler<StatusLine> responseHandler = responseHandlerFactory.getInstanceOfResponseHandler();
		HttpGet request = new HttpGet(this.getUri());
		return httpClient.execute(request, responseHandler);
	}

}

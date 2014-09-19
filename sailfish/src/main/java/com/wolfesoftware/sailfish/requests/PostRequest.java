package com.wolfesoftware.sailfish.requests;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;

import com.wolfesoftware.sailfish.responsehandler.factory.ResponseHandlerFactory;

public class PostRequest extends SimpleRequest implements Request {

	public PostRequest(String uri) throws MalformedURLException {
		super(uri);
	}

	@Override
	public StatusLine makeRequest(HttpClient httpClient) throws ClientProtocolException, IOException {
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory.getInstanceOfResponseHandler();
		return httpClient.execute(new HttpPost(this.getUri()), responseHandler);
	}

}

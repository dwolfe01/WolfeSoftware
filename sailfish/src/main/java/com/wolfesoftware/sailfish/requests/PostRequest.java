package com.wolfesoftware.sailfish.requests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.wolfesoftware.sailfish.responsehandler.factory.ResponseHandlerFactory;

public class PostRequest extends SimpleRequest implements Request {

	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	public PostRequest(String uri) throws MalformedURLException {
		super(uri);
	}

	public void addNameValuePostPair(String name, String value) {
		nameValuePairs.add(new BasicNameValuePair(name, value));
	}

	public HttpPost build() throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(this.getUri());
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		return httpPost;
	}

	@Override
	public StatusLine makeRequest(HttpClient httpClient) throws ClientProtocolException, IOException {
		ResponseHandler<StatusLine> responseHandler = ResponseHandlerFactory.getInstanceOfResponseHandler();
		HttpPost httpPost = this.build();
		return httpClient.execute(httpPost, responseHandler);
	}

}

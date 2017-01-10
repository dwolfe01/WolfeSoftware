package com.wolfesoftware.sailfish.http.requests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.sailfish.http.main.SailFish;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;

public class PostRequest extends AbstractRequest {

	private static final Logger Logger = LoggerFactory.getLogger(PostRequest.class);
	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	public PostRequest(String uri) throws URISyntaxException  {
		super(uri);
	}

	public void addNameValuePostPair(String name, String value) {
		nameValuePairs.add(new BasicNameValuePair(name, value));
	}

	public HttpPost build() throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(this.getUri());
		httpPost.setHeader("REFERER", "http://sailfish.com");
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		return httpPost;
	}

	@Override
	public StatusLine makeRequest(HttpClient httpClient, ResponseHandlerFactory responseHandlerFactory) throws ClientProtocolException, IOException {
		HttpPost httpPost = this.build();
		Logger.info(this.toString(httpPost, httpClient));
		return httpClient.execute(httpPost, responseHandlerFactory.getInstanceOfResponseHandler());
	}
	
	public String toString(HttpPost httpPost, HttpClient httpClient){
		String returnString = "";
		returnString += "Headers...";
		for ( Header header:httpPost.getAllHeaders()){
			returnString += header.getName() + " value:" + header.getValue() + "\n";
		}
		returnString += "Body...";
		returnString += httpPost.getEntity() +  "\n";
		try {
			List<String> readLines;
			readLines = IOUtils.readLines(httpPost.getEntity().getContent());
			for (String s: readLines){
				returnString += readLines +  "\n";
			}
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnString;
		
	}
	
	

}

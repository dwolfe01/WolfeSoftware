package com.wolfesoftware.pact;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

public class Consumer {

	private String baseUrl;

	public Consumer(@Value("${user-service.base-url}") String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getUser(String id) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(new HttpGet(baseUrl + "/getUser"));
		String content = EntityUtils.toString(response.getEntity());
		return content.toUpperCase();
	}

}

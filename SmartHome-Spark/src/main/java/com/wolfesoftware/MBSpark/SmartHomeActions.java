package com.wolfesoftware.MBSpark;

import static spark.Spark.halt;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartHomeActions {
	
	private final String hueHome = "http://192.168.1.81/api/4nHIqq4Th90cUZpf66Lr-2nxtLoLSJ6UnT5lFJBA";
	private final Logger LOGGER = LoggerFactory.getLogger(SmartHomeActions.class);
	
	protected String getLights() {
		String jsonObjectFromEndpoint = "";
		try {
			jsonObjectFromEndpoint = this.getJSONObjectFromEndpoint(hueHome + "/lights");
		} catch (Exception e) {
			// replace with custom error page
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return jsonObjectFromEndpoint;
	}

	protected void sendMessageToGroup(String groupId, String message) {
		String jsonObjectFromEndpoint = null;
		try {
			jsonObjectFromEndpoint = this.putJSONObjectFromEndpoint(hueHome + "/groups/" + groupId + "/action",
					message);
		} catch (Exception e) {
			// replace with custom error page
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		LOGGER.info(jsonObjectFromEndpoint);
	}

	private String putJSONObjectFromEndpoint(String url, String body) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(body));
		HttpResponse response = client.execute(request);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String responseBody = EntityUtils.toString(response.getEntity());
		System.out.println(responseBody);
		return responseBody;
	}

	private String getJSONObjectFromEndpoint(String url) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String responseBody = EntityUtils.toString(response.getEntity());
		return responseBody;
	}


}

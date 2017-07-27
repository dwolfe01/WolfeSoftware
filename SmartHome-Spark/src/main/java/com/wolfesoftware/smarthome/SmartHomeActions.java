package com.wolfesoftware.smarthome;

import static spark.Spark.halt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SmartHomeActions {

	private final String hueHome = "http://192.168.1.81/api/4nHIqq4Th90cUZpf66Lr-2nxtLoLSJ6UnT5lFJBA";
	private final Logger LOGGER = LoggerFactory.getLogger(SmartHomeActions.class);

	protected List<String> getLights() {
		List<String> descriptionOfLights = new ArrayList<String>();
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(this.getJSONObjectFromEndpoint(hueHome + "/lights"));
			JsonObject lights = element.getAsJsonObject();
			for (String key:lights.keySet()){
				JsonObject individualLight = lights.getAsJsonObject(key);
				descriptionOfLights.add(key + ":" + individualLight.get("name") + " " + individualLight.get("state"));
			}
		} catch (Exception e) {
			// replace with custom error page
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return descriptionOfLights;
	}
	
	protected List<String> getGroups() {
		List<String> descriptionOfLights = new ArrayList<String>();
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(this.getJSONObjectFromEndpoint(hueHome + "/groups"));
			JsonObject lights = element.getAsJsonObject();
			for (String key:lights.keySet()){
				JsonObject individualLight = lights.getAsJsonObject(key);
				descriptionOfLights.add(key + ":" + individualLight.get("name") + " " + this.getOnCommand(key)+ " " + this.getOffCommand(key));
			}
		} catch (Exception e) {
			// replace with custom error page
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return descriptionOfLights;
	}
	
	
	private String getOnCommand(String key) {
		String onImage="<img src=\"/lightbulb_on.png\" style=\"width:50px;height:50px;\">";
		return "<a href=\"/on?groupId=" + key + "\">" + onImage + "</a>";
	}
	
	private String getOffCommand(String key) {
		String offImage="<img src=\"/lightbulb_off.jpg\" style=\"width:50px;height:50px;\">";
		return "<a href=\"/off?groupId=" + key + "\">" + offImage + "</a>";
	}

	public void sendMessageToLight(String groupId, String message) {
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

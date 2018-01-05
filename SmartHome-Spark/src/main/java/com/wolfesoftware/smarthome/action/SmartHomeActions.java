package com.wolfesoftware.smarthome.action;

import static spark.Spark.halt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

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
import com.wolfesoftware.smarthome.SmartProperties;
import com.wolfesoftware.smarthome.web.XMLBoss;

public class SmartHomeActions {

	private final String hueHome = SmartProperties.get("hueHome");
	private final Logger LOGGER = LoggerFactory.getLogger(SmartHomeActions.class);
	private HttpClient client;
	
	public SmartHomeActions(){
		this(HttpClientBuilder.create().build());
	}
	
	public SmartHomeActions(HttpClient client){
		this.client = client;
	}
	
	public String getWeather() throws ClientProtocolException, IOException, TransformerException {
		String weatherResult = XMLBoss.xsl("xsl/weather.xsl", this.getWeatherFromBBC());
		return weatherResult;
	}
	
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
	
	public List<String> getGroups() {
		List<String> descriptionOfLights = new ArrayList<String>();
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(this.getJSONObjectFromEndpoint(hueHome + "/groups"));
			JsonObject lights = element.getAsJsonObject();
			for (String key:lights.keySet()){
				JsonObject individualLight = lights.getAsJsonObject(key);
				String name = individualLight.get("name").getAsString().replaceAll("\"", "");
				String string = name + " " + this.getOnCommand(key)+ " " + this.getOffCommand(key);
				descriptionOfLights.add(string);
			}
		} catch (Exception e) {
			// replace with custom error page
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return descriptionOfLights;
	}
	
	public void turnHueGroupOn(String groupId) {
		sendMessageToGroup(groupId, "{\"on\":true,\"bri\":100,\"sat\":100,\"hue\":0}");
	}
	
	public void turnHueGroupOff(String groupId) {
		sendMessageToGroup(groupId, "{\"on\":false}");
	}
	
	private String getOnCommand(String key) {
		String onImage="<img src=\"/lightbulbOn.png\" style=\"width:75px;height:40px;\">";
		return "<a href=\"/on?groupId=" + key + "\">" + onImage + "</a>";
	}
	
	private String getOffCommand(String key) {
		String offImage="<img src=\"/lightbulbOff.png\" style=\"width:75px;height:40px;\">";
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
	
	public void sendMessageToGroup(String groupId, String message) {
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
	
	private String getWeatherFromBBC() throws ClientProtocolException, IOException{
		HttpGet request = new HttpGet("http://open.live.bbc.co.uk/weather/feeds/en/2643027/3dayforecast.rss");
		HttpResponse response = client.execute(request);
		String responseBody = EntityUtils.toString(response.getEntity());
		LOGGER.info(responseBody);
		return responseBody;
	}

	private String putJSONObjectFromEndpoint(String url, String body) throws ClientProtocolException, IOException {
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(body));
		HttpResponse response = client.execute(request);
		String responseBody = EntityUtils.toString(response.getEntity());
		LOGGER.info(responseBody);
		return responseBody;
	}

	private String getJSONObjectFromEndpoint(String url) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		String responseBody = EntityUtils.toString(response.getEntity());
		LOGGER.info(responseBody);
		return responseBody;
	}

	public String startRadio(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}

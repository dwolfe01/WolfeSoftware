package com.wolfesoftware.MBSpark;

import static spark.Spark.halt;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

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

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Request;
import spark.Response;

public class SmartHomeEndpoints {

	private Configuration configuration;
	private final Logger LOGGER = LoggerFactory.getLogger(SmartHomeEndpoints.class);
	private final String hueHome = "http://192.168.1.81/api/4nHIqq4Th90cUZpf66Lr-2nxtLoLSJ6UnT5lFJBA";

	public SmartHomeEndpoints() {
		configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(SmartHomeEndpoints.class, "/");
	}

	protected StringWriter getIndexPage(Request request, Response response) {
		StringWriter writer = new StringWriter();
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			String jsonObjectFromEndpoint = this.getJSONObjectFromEndpoint(hueHome + "/lights");
			model.put("json", jsonObjectFromEndpoint);
			System.out.println(jsonObjectFromEndpoint);
			Template formTemplate = configuration.getTemplate("templates/index.ftl");
			formTemplate.process(model, writer);
		} catch (Exception e) {
			// replace with custom error page
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return writer;
	}
	
	protected StringWriter on(Request request, Response response) {
		StringWriter writer = new StringWriter();
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			String jsonObjectFromEndpoint = this.putJSONObjectFromEndpoint("http://192.168.1.81/api/4nHIqq4Th90cUZpf66Lr%2d2nxtLoLSJ6UnT5lFJBA/groups/0/action", "{\"on\":false}");
			model.put("json", jsonObjectFromEndpoint);
			System.out.println(jsonObjectFromEndpoint);
			Template formTemplate = configuration.getTemplate("templates/index.ftl");
			formTemplate.process(model, writer);
		} catch (Exception e) {
			// replace with custom error page
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return writer;
	}

	private String getJSONObjectFromEndpoint(String url) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

		String responseBody = EntityUtils.toString(response.getEntity());
		return responseBody;
	}
	
	private String putJSONObjectFromEndpoint(String url, String body) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(body));
		HttpResponse response = client.execute(request);
		System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

		String responseBody = EntityUtils.toString(response.getEntity());
		System.out.println(responseBody);
		return responseBody;
	}
	
		
}

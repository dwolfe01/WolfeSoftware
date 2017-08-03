package com.wolfesoftware.smarthome;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import spark.Request;
import spark.Response;

public class SmartHomeEndpoints {

	private Configuration configuration;
//	private final Logger LOGGER = LoggerFactory.getLogger(SmartHomeEndpoints.class);
	private SmartHomeActions smartHomeActions = new SmartHomeActions();

	public SmartHomeEndpoints() {
		configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(SmartHomeEndpoints.class, "/");
	}

	protected StringWriter homepage(Request request, Response response) throws TemplateException, IOException {
		StringWriter writer = new StringWriter();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("listOfLights", smartHomeActions.getGroups());
		Template formTemplate = configuration.getTemplate("templates/dolly.ftl");
		formTemplate.process(model, writer);
		return writer;
	}
	
	protected StringWriter dolly(Request request, Response response) throws TemplateException, IOException {
		StringWriter writer = new StringWriter();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("listOfLights", smartHomeActions.getGroups());
		Template formTemplate = configuration.getTemplate("templates/index.ftl");
		formTemplate.process(model, writer);
		return writer;
	}

	protected StringWriter on(Request request, Response response) throws TemplateException, IOException {
		String groupId = request.queryParams("groupId");
		smartHomeActions.sendMessageToGroup(groupId, "{\"on\":true,\"bri\":100,\"sat\":100,\"hue\":0}");
		sleepy();
		return this.homepage(request, response);
	}

	private void sleepy() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected StringWriter off(Request request, Response response) throws TemplateException, IOException {
		String groupId = request.queryParams("groupId");
		smartHomeActions.sendMessageToGroup(groupId, "{\"on\":false}");
		sleepy();
		return this.homepage(request, response);
	}
	
	protected StringWriter lightOn(Request request, Response response) throws TemplateException, IOException {
		String id = request.queryParams("id");
		smartHomeActions.sendMessageToLight(id, "{\"on\":true}");
		sleepy();
		return this.homepage(request, response);
	}

}

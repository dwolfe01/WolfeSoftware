package com.wolfesoftware.MBSpark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		model.put("listOfLights", smartHomeActions.getLights());
		Template formTemplate = configuration.getTemplate("templates/index.ftl");
		formTemplate.process(model, writer);
		return writer;
	}

	protected StringWriter on(Request request, Response response) throws TemplateException, IOException {
		String groupId = request.queryParams("groupId");
		smartHomeActions.sendMessageToGroup(groupId, "{\"on\":true}");
		return this.homepage(request, response);
	}

	protected StringWriter off(Request request, Response response) throws TemplateException, IOException {
		String groupId = request.queryParams("groupId");
		smartHomeActions.sendMessageToGroup(groupId, "{\"on\":false}");
		return this.homepage(request, response);
	}

}

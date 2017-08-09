package com.wolfesoftware.smarthome.web;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.wolfesoftware.smarthome.action.SmartHomeActions;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;
import spark.Request;
import spark.Response;

public class SmartHomeEndpoints {

	private Configuration configuration;
	// private final Logger LOGGER =
	// LoggerFactory.getLogger(SmartHomeEndpoints.class);
	private SmartHomeActions smartHomeActions = new SmartHomeActions();
	private SmartHomeTemplates smartHomeTemplates = new SmartHomeTemplates();

	public SmartHomeEndpoints() {
		configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(SmartHomeEndpoints.class, "/");
	}

	protected StringWriter homepage(Request request, Response response) throws TemplateException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		// populate model here to match tags in the freemarker
		model.putAll(this.getRightHandNav());
		StringWriter writer = processPage(smartHomeTemplates.getTemplate1(model), smartHomeTemplates.getTemplate1CSS(null));
		return writer;
	}
	
	public StringWriter weather(Request request, Response response) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, Object> model = new HashMap<String, Object>();
		// populate model here to match tags in the freemarker
		model.putAll(this.getRightHandNav());
		model.put("body", smartHomeActions.getWeather());
		StringWriter writer = processPage(smartHomeTemplates.getTemplate2(model), smartHomeTemplates.getTemplate2CSS(null));
		return writer;
	}
	
	private Map<String, Object> getRightHandNav() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nav1", createLink("Lights"));
		map.put("nav2", createLink("Music"));
		map.put("nav3", createLink("Twitter"));
		map.put("nav4", createLink("News"));
		map.put("nav5", createLink("Weather"));
		return map;
	}

	private String createLink(String navLink) {
		// TODO Auto-generated method stub
		return "<a href=\"" + navLink.toLowerCase() + "\">" + navLink + "</a>";
	}

	protected StringWriter on(Request request, Response response) throws TemplateException, IOException {
		String groupId = request.queryParams("groupId");
		smartHomeActions.sendMessageToGroup(groupId, "{\"on\":true,\"bri\":100,\"sat\":100,\"hue\":0}");
		sleepy();
		return this.homepage(request, response);
	}

	protected StringWriter off(Request request, Response response) throws TemplateException, IOException {
		String groupId = request.queryParams("groupId");
		smartHomeActions.sendMessageToGroup(groupId, "{\"on\":false}");
		sleepy();
		return this.homepage(request, response);
	}

	private StringWriter processPage(String template, String css) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("templates/dolly.ftl");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("css", css);
		model.put("body", template);
		formTemplate.process(model, writer);
		return writer;
	}

	private void sleepy() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

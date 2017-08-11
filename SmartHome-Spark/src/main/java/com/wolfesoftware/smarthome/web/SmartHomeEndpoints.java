package com.wolfesoftware.smarthome.web;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerException;

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
	private SmartHomeHTML smartHomeHTML = new SmartHomeHTML();

	public SmartHomeEndpoints() {
		configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(SmartHomeEndpoints.class, "/");
	}

	protected StringWriter homepage(Request request, Response response) throws TemplateException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		// populate model here to match tags in the freemarker
		model.putAll(smartHomeHTML.getRightHandNav());
		StringWriter writer = smartHomeHTML.createHTMLPage(smartHomeHTML.getTemplate1(model), smartHomeHTML.getTemplate1CSS(null));
		return writer;
	}
	
	public StringWriter weather(Request request, Response response) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException, TransformerException {
		Map<String, Object> model = new HashMap<String, Object>();
		// populate model here to match tags in the freemarker
		model.putAll(smartHomeHTML.getRightHandNav());
		model.put("body", smartHomeActions.getWeather());
		StringWriter writer = smartHomeHTML.createHTMLPage(smartHomeHTML.getTemplate2(model), smartHomeHTML.getTemplate2CSS(null));
		return writer;
	}
	
	protected StringWriter on(Request request, Response response) throws TemplateException, IOException {
		String groupId = request.queryParams("groupId");
		smartHomeActions.turnHueGroupOn(groupId);
		return this.homepage(request, response);
	}

	protected StringWriter off(Request request, Response response) throws TemplateException, IOException {
		String groupId = request.queryParams("groupId");
		smartHomeActions.turnHueGroupOff(groupId);
		return this.homepage(request, response);
	}

}

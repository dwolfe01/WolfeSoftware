package com.wolfesoftware.smarthome.web;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

public class SmartHomeTemplates {
	
	private Configuration configuration;

	SmartHomeTemplates(){
		configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(SmartHomeEndpoints.class, "/");
	}

	public String getTemplate1(Map<String, Object> model) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("templates/template1.ftl");
		formTemplate.process(model, writer);
		return writer.toString();
	}

	public String getTemplate1CSS(Map<String, Object> model) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException  {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("css/template1.ftl");
		formTemplate.process(model, writer);
		return writer.toString();
	}

}

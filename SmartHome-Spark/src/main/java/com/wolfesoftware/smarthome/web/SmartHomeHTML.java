package com.wolfesoftware.smarthome.web;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

public class SmartHomeHTML {

	private Configuration configuration;

	SmartHomeHTML() {
		configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(SmartHomeEndpoints.class, "/");
	}

	public String getTemplate1(Map<String, Object> model) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("templates/template1.ftl");
		formTemplate.process(model, writer);
		return writer.toString();
	}

	public String getTemplate1CSS(Map<String, Object> model) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("css/template1css1.ftl");
		formTemplate.process(model, writer);
		return writer.toString();
	}

	public String getTemplate2(Map<String, Object> model) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("templates/template2.ftl");
		formTemplate.process(model, writer);
		return writer.toString();
	}

	public String getTemplate2CSS(Map<String, Object> model) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("css/template2css1.ftl");
		formTemplate.process(model, writer);
		return writer.toString();
	}

	public String getTemplate3(Map<String, Object> model) throws TemplateException, IOException {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("templates/template3.ftl");
		formTemplate.process(model, writer);
		return writer.toString();
	}

	public String getTemplate3CSS(Map<String, Object> model) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("css/template3css1.ftl");
		formTemplate.process(model, writer);
		return writer.toString();
	}

	public Map<String, Object> getRightHandNav() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nav1", createLink("Lights"));
		map.put("nav2", createLink("Music"));
		map.put("nav3", createLink("Twitter"));
		map.put("nav4", createLink("News"));
		map.put("nav5", createLink("Weather"));
		return map;
	}

	public StringWriter createHTMLPage(String template, String css) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringWriter writer = new StringWriter();
		Template formTemplate = configuration.getTemplate("templates/dolly.ftl");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("css", css);
		model.put("body", template);
		formTemplate.process(model, writer);
		return writer;
	}

	private String createLink(String navLink) {
		// TODO Auto-generated method stub
		return "<a href=\"" + navLink.toLowerCase() + "\">" + navLink + "</a>";
	}

}

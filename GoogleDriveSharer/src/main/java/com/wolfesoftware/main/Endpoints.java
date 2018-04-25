package com.wolfesoftware.main;

import static spark.Spark.halt;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Request;
import spark.Response;

public class Endpoints {

	private Configuration configuration;
	private final Logger LOGGER = LoggerFactory.getLogger(Endpoints.class);

	public Endpoints() {
		configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(Endpoints.class, "/");
	}

	protected StringWriter getRobots(Request request, Response response) {
		StringWriter writer = new StringWriter();
		response.type("text/plain");
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			Template formTemplate = configuration.getTemplate("templates/robots.ftl");
			formTemplate.process(model, writer);
		} catch (Exception e) {
			// replace with custom error page
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return writer;
	}

	public StringWriter getIndexPage(Request request, Response response) {
		StringWriter writer = new StringWriter();
		try {
			Template formTemplate = configuration.getTemplate("templates/index.ftl");
			formTemplate.process(null, writer);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return writer;
	}

	public Object getLoginPage(Request request, Response response) {
		StringWriter writer = new StringWriter();
		try {
			Template formTemplate = configuration.getTemplate("templates/login.ftl");
			formTemplate.process(null, writer);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return writer;
	}


}

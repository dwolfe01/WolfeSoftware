package com.wolfesoftware.MBSpark;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.staticFileLocation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

public class MB {

	final Configuration configuration;
	private static final Logger LOGGER = LoggerFactory.getLogger(MB.class);

	public static void main(String[] args) {
		new MB();
	}

	public MB() {
		configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(MB.class, "/");
		staticFileLocation("/public");
		createFilters();
		createEndpoints();
	}

	private void createFilters() {
		before((req, res) -> {
			LOGGER.info(req.ip() + " " + req.uri());
		});
	}

	private void createEndpoints() {
		get("/index", (request, response) -> getRequestInfo(request, response));
	}

	private StringWriter getRequestInfo(Request request, Response response) {
		StringWriter writer = new StringWriter();
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			FileInputStream is = new FileInputStream(new File("requests.log"));
			model.put("logfile", new Stats().getLogs(is,request.ip()));
			is.close();
			Template formTemplate = configuration.getTemplate("templates/index.ftl");
			formTemplate.process(model, writer);
		} catch (Exception e) {
			halt(500);
		}
		return writer;
	}

}

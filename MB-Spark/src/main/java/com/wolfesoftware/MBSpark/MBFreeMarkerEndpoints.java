package com.wolfesoftware.MBSpark;

import static spark.Spark.halt;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.entity.LogMessage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Request;
import spark.Response;

public class MBFreeMarkerEndpoints {
	
	private Configuration configuration;
	private final Logger LOGGER = LoggerFactory.getLogger(MBFreeMarkerEndpoints.class);
	
	public MBFreeMarkerEndpoints(){
		configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(MBFreeMarkerEndpoints.class, "/");
	}

	protected StringWriter getIndexPage(Request request, Response response) {
		StringWriter writer = new StringWriter();
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			FileInputStream is = new FileInputStream(new File("requests.log"));
			List<LogMessage> logs = new Stats().getLogs(is,request.ip());
			model.put("logfile", logs);
			model.put("blocked", logs.size()>10);
			is.close();
			Template formTemplate = configuration.getTemplate("templates/index.ftl");
			formTemplate.process(model, writer);
		} catch (Exception e) {
			//replace with custom error page
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return writer;
	}
	
	
}

package com.wolfesoftware.MBSpark;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MB {

	private static final Logger LOGGER = LoggerFactory.getLogger(MB.class);
	MBFreeMarkerEndpoints mbFreeMarkerEndpoints = new MBFreeMarkerEndpoints();

	public static void main(String[] args) {
		new MB();
	}

	public MB() {
		staticFileLocation("/public");
		createFilters();
		createEndpoints();
	}

	private void createEndpoints() {
		get("/", (request, response) -> {
			return mbFreeMarkerEndpoints.getIndexPage(request, response);
		});
		get("/robots.txt", (request, response) -> {
			return mbFreeMarkerEndpoints.getRobots(request, response);
		});
		get("/message.txt", (request, response) -> {
			return mbFreeMarkerEndpoints.getRobots(request, response);
		});
		post("/questions", (request, response) -> {
			return mbFreeMarkerEndpoints.processQuestions(request, response);
		});
	}

	private void createFilters() {
		before((req, res) -> {
			LOGGER.info(req.ip() + " " + req.uri());
		});
	}

}

package com.wolfesoftware.smarthome;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartHome {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmartHome.class);
	SmartHomeEndpoints mbFreeMarkerEndpoints = new SmartHomeEndpoints();

	public static void main(String[] args) {
		new SmartHome();
	}

	public SmartHome() {
		staticFileLocation("/public");
		createFilters();
		createEndpoints();
	}

	private void createEndpoints() {
		get("/", (request, response) -> {
			return mbFreeMarkerEndpoints.homepage(request, response);
		});
		get("/dolly", (request, response) -> {
			return mbFreeMarkerEndpoints.dolly(request, response);
		});
		get("/on", (request, response) -> {
			return mbFreeMarkerEndpoints.on(request, response);
		});
		get("/off", (request, response) -> {
			return mbFreeMarkerEndpoints.off(request, response);
		});
	}

	private void createFilters() {
		before((req, res) -> {
			LOGGER.info(req.ip() + " " + req.uri());
		});
	}

}

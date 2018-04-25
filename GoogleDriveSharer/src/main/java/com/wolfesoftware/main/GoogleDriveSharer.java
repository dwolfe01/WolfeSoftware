package com.wolfesoftware.main;

import static spark.Spark.before;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleDriveSharer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveSharer.class);
	Endpoints endpoints = new Endpoints();

	public static void main(String[] args) {
		new GoogleDriveSharer();
	}

	public GoogleDriveSharer() {
		port(4568);
		staticFileLocation("/public");
		createFilters();
		createEndpoints();
	}

	private void createEndpoints() {
		get("/", (request, response) -> {
			return endpoints.getIndexPage(request, response);
		});
		get("/login", (request, response) -> {
			return endpoints.getLoginPage(request, response);
		});
		post("/login", (request, response) -> {
			String username = request.queryParams("uname");
			String password = request.queryParams("psw");
			LOGGER.info("Username attempting login:" + username);
			if (null!=username && null!=password && username.equals("tamsyn") && password.equals("xoxoxo9021")) {
					request.session(true).attribute("user", "tamsyn");
					response.redirect("/", 302);
					return null;
			} else {
				return endpoints.getLoginPage(request, response);
			}
		});
	}

	private void createFilters() {
		before("/",(request, response) -> {
			LOGGER.info(request.ip() + " " + request.uri());
			if (request.session(true).attribute("user") == null) {
				response.redirect("/login", 302);
			}
		});
	}

}

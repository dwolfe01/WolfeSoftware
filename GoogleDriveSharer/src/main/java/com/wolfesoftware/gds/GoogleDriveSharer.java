package com.wolfesoftware.gds;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;
import static spark.Spark.stop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.gds.users.User;
import com.wolfesoftware.gds.users.UsersAPI;
import com.wolfesoftware.gds.users.sessionmanager.SessionManager;

import spark.Request;
import spark.Response;

public class GoogleDriveSharer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveSharer.class);
	Endpoints endpoints = new Endpoints();
	
	public static void main(String[] args) {
		User user = new User();
		user.setFirstName("Test");
		user.setLastName("Test");
		user.setId(args[0]);
		user.setPassword(args[1]);
		new UsersAPI().addUser(user);
		new GoogleDriveSharer();
	}

	public GoogleDriveSharer() {
		port(4568);
		staticFileLocation("/public");
		createFilters();
		createEndpoints();
	}
	
	public void exit() {
		stop();
	}

	private void createEndpoints() {
		get("/", (request, response) -> {
			return endpoints.getIndexPage(request, response);
		});
		get("/login", (request, response) -> {
			return endpoints.getLoginPage(request, response);
		});
		get("/robots.txt", (request, response) -> {
			return endpoints.getRobots(request, response);
		});
		get("/upload", (request, response) -> {
			return endpoints.upload(request, response);
		});
		post("/update/:id", (request, response) -> {
			endpoints.updateDescription(request.params(":id"), request.body());
			return "Job Done";
		});
		post("/uploadPicture", (request, response) -> {
		    return endpoints.saveFile(request, response);
		});
		get("/test", (request, response) -> {
			SessionManager.setAttribute("folder", com.wolfesoftware.gds.configuration.Configuration.get("test.folder.in.google.drive"), request);
			response.redirect("/", 302);
			return "You are now using this site in test mode, recreate session to remove that";
		});
		post("/login", (request, response) -> {
			String username = request.queryParams("uname");
			String password = request.queryParams("psw");
			LOGGER.info("Username attempting login:" + username);
			SessionManager.login(username, password, request);
			if (SessionManager.isLoggedIn(request)) {
					response.redirect("/", 302);
					return null;
			} else {
				return endpoints.getLoginPage(request, response);
			}
		});
	}


	private void createFilters() {
		before((request, response) -> {
			doBeforeFilter(request, response);
		});
	}
	
	private void doBeforeFilter(Request request, Response response) {
		LOGGER.info(request.ip() + " " + request.uri());
		setupDefaultFolder(request);
		if (!(SessionManager.isLoggedIn(request)) && !request.uri().equals("/login") && !request.uri().startsWith("/update")) {
			response.redirect("/login", 302);
		}
	}

	private void setupDefaultFolder(Request request) {
		if (null==SessionManager.getAttribute("folder", request)) {
			SessionManager.setAttribute("folder", com.wolfesoftware.gds.configuration.Configuration.get("folder.in.google.drive"), request);
		}
	}

}

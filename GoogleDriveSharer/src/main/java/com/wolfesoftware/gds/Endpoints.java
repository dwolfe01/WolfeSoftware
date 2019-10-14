package com.wolfesoftware.gds;

import static spark.Spark.halt;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.DateTime;
import com.google.api.services.drive.model.File;
import com.wolfesoftware.gds.dayselapsed.TimeElapsedSince;
import com.wolfesoftware.gds.drive.api.DriveAPI;
import com.wolfesoftware.gds.users.sessionmanager.SessionManager;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Request;
import spark.Response;

public class Endpoints {

	private Configuration configuration;
	private final Logger LOGGER = LoggerFactory.getLogger(Endpoints.class);
	DriveAPI driveAPI = new DriveAPI();

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
			List<File> files = driveAPI.listFiles(50, (String)SessionManager.getAttribute("folder", request));
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("files", files);
			model.put("header", this.getHeader(request));
			model.put("dateFormatter", (Function<DateTime, String>)this::formatDate);
			formTemplate.process(model, writer);
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

	public String getHeader(Request request) {
		StringWriter writer = new StringWriter();
		try {
			Template formTemplate = configuration.getTemplate("templates/header.ftl");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("jagosAge", TimeElapsedSince.timeSinceJagosBirth(LocalDateTime.now()));
			model.put("kizzysAge", TimeElapsedSince.timeSinceKizzysBirth(LocalDateTime.now()));
			formTemplate.process(model, writer);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return writer.toString();
	}

	public StringWriter upload(Request request, Response response) {
		StringWriter writer = new StringWriter();
		try {
			Template formTemplate = configuration.getTemplate("templates/upload.ftl");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("message", request.attribute("message"));
			model.put("header", this.getHeader(request));
			formTemplate.process(model, writer);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			halt(500);
		}
		return writer;
	}

	public StringWriter saveFile(Request request, Response response) throws IOException, ServletException {
		request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
		Part part = request.raw().getPart("uploaded_file");
		try (InputStream is = part.getInputStream()) {
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
			if (buffer.length == 0) {
				response.redirect("/upload", 302);
				return null;
			}
			;
			driveAPI.upload(buffer, (String)SessionManager.getAttribute("folder", request), this.getFileName(part));
		}
		request.attribute("message", "File Uploaded");
		return this.upload(request, response);
	}

	private String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return "no_file_name";
	}
	
	public String formatDate(DateTime date) {
		return formatDate(date.toString());
	}

	public String formatDate(String date) {
		Instant instant = Instant.parse(date);
		LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		return dtf.format(ldt);
	}

	public void updateDescription(String id, String description) throws IOException {
		driveAPI.setDescription(id, description);
	}

}

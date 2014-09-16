package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfesoftware.sailfish.runnable.httpuser.HttpUser;
import com.wolfesoftware.sailfish.runnable.httpuser.JustOutputHttpUser;

/*
 * This class creates a worker and also returns it. This will
 * potentially reads from an tomcat access log file to generate different
 * types of sessions on each call to getWorker
 */
//TODO this should use generics to achieve the dry run functionality 
public class HttpUserWorkerFactoryFromJSONFile extends WorkerFactory {

	private String json;
	HttpUser[] users;
	int position;
	Class<? extends HttpUser[]> user = HttpUser[].class;

	@Override
	public HttpUser getWorker() throws JsonParseException,
			JsonMappingException, IOException {
		if (position + 1 == users.length) {
			this.setIsThereAnyMoreWorkToDo(false);
		}
		return users[position++];
	}

	public void setJSON(String json) throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		users = objectMapper.readValue(json, user);
		this.setJson(json);
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setDryRun(boolean b) {
		if (b) {
			user = JustOutputHttpUser[].class;
		} else {
			user = HttpUser[].class;
		}
	}

}

package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfesoftware.sailfish.runnable.httpuser.HttpUser;

/*
 * This class creates a worker and also returns it. This will
 * potentially reads from an tomcat access log file to generate different
 * types of sessions on each call to getWorker
 */
public class HttpUserWorkerFactoryFromJSONFile extends WorkerFactory {

	private String json;
	HttpUser user;

	@Override
	public HttpUser getWorker() throws JsonParseException,
			JsonMappingException, IOException {
		this.setIsThereAnyMoreWorkToDo(false);
		return user;
	}

	public void setJSON(String json) throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		user = objectMapper.readValue(json, HttpUser.class);
		this.json = json;
	}

}

package com.wolfesoftware.sailfish.http.worker.factory;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfesoftware.sailfish.core.concurrency.WorkerFactory;
import com.wolfesoftware.sailfish.http.runnable.httpuser.HttpUser;

/*
 * This class creates a worker and also returns it. This will
 * potentially reads from an tomcat access log file to generate different
 * types of sessions on each call to getWorker
 */
//TODO this should use generics to achieve the dry run functionality 
public class HttpUserWorkerFactoryFromJSONFile extends WorkerFactory {

	HttpUser[] users;
	int position;
	ObjectMapper objectMapper;

	public HttpUserWorkerFactoryFromJSONFile(String json) throws JsonParseException, JsonMappingException, IOException {
		objectMapper = new ObjectMapper();
		users = objectMapper.readValue(json, HttpUser[].class);
	}

	@Override
	public HttpUser getWorker() throws JsonParseException,
			JsonMappingException, IOException {
		if (position + 1 == users.length) {
			this.setIsThereAnyMoreWorkToDo(false);
		}
		return users[position++];
	}
	
	@Override
	public String toString(){
		StringWriter usersString = new StringWriter();
		try {
			objectMapper.writeValue(usersString, users);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usersString.toString();
	}

	
}

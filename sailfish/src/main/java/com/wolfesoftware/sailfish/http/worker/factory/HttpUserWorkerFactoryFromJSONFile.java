package com.wolfesoftware.sailfish.http.worker.factory;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wolfesoftware.sailfish.core.concurrency.WorkerFactory;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.runnable.httpuser.HttpUser;

public class HttpUserWorkerFactoryFromJSONFile extends WorkerFactory {

	HttpUser[] users;
	int position;
	ObjectMapper objectMapper;

	public HttpUserWorkerFactoryFromJSONFile(String json, ResponseHandlerFactory responseHandlerFactory) throws JsonParseException, JsonMappingException, IOException {
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		users = objectMapper.readValue(json, HttpUser[].class);
		for (HttpUser user:users){
			user.setResponseHandlerFactory(responseHandlerFactory);
		}
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
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return usersString.toString();
	}

	
}

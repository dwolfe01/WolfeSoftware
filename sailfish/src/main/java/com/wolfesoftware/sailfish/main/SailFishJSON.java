package com.wolfesoftware.sailfish.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.HttpUserWorkerFactoryFromJSONFile;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.responsehandler.ResponseHandlerFactory.ResponseHandlers;

public class SailFishJSON {

	///example file name: Users/dwolfe/development/WolfeSoftware/WolfeSoftware/sailfish/src/test/resources/com/wolfesoftware/sailfish/json/httpuser/httpuser.json
	
	
	public static void main(String[] args) throws BadLogFileException, BadLogFileException, JsonParseException,
			JsonMappingException, IOException, NoSuchMethodException {
		String fileName = args[0];
		int threadCount = Integer.parseInt(args[1]);
		System.out.println(("Running SailFish with json file: " + fileName + " thread count: " + threadCount));
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile(
				FileUtils.readFileToString(new File(fileName)));
		ResponseHandlerFactory.setHandler(ResponseHandlers.SYSTEMOUT);
		new ReadySteadyThread(1, factory).go();
	}

}

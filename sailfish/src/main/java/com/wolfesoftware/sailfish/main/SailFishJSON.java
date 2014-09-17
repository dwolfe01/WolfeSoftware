package com.wolfesoftware.sailfish.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.HttpUserWorkerFactoryFromJSONFile;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;

public class SailFishJSON {

	public static void main(String[] args) throws BadLogFileException, BadLogFileException, JsonParseException, JsonMappingException, IOException {
		String fileName = args[0];
		int threadCount = Integer.parseInt(args[1]);
		System.out.println(("Running SailFish with file: " + fileName + " thread count: " + threadCount));
		SailFishJSON sailfish = new SailFishJSON();
		File jsonFile = new File(fileName);
		sailfish.go(jsonFile, threadCount);
	}

	private void go(File jsonFile, int threadCount) throws BadLogFileException, JsonParseException, JsonMappingException, IOException {
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		factory.setDryRun(true);
		factory.setJSON(FileUtils.readFileToString(jsonFile));
		new ReadySteadyThread(10, factory).go();
	}

}

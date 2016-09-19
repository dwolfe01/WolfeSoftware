package com.wolfesoftware.sailfish.http.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wolfesoftware.sailfish.core.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.core.concurrency.WorkerFactory;
import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromJSONFile;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromLogFile;
import com.wolfesoftware.sailfish.http.worker.factory.UptimeHttpUserWorkerFactoryFromLogFile;

public class SailFish {

	public static void main(String[] args) throws 
			Exception {
		String fileName = args[0];
		int threadCount = Integer.parseInt(args[1]);
		System.out.println(("Running SailFish with file: " + fileName
				+ " thread count: " + threadCount));
		SailFish sailfish = new SailFish();
		File logFile = new File(fileName);
		System.out.println(logFile.getAbsolutePath());
		sailfish.go(logFile, threadCount);
	}

	private void go(File logFile, int threadCount) throws BadLogFileException, JsonParseException, JsonMappingException, IOException {
		WorkerFactory factory = null;
		if (logFile.getAbsolutePath().endsWith("json")){
			factory = new HttpUserWorkerFactoryFromJSONFile(FileUtils.readFileToString(logFile));
		} 
		if (logFile.getAbsolutePath().endsWith("uptime")){
			LogFileReader logFileReader = new LogFileReader(logFile);
			factory = new UptimeHttpUserWorkerFactoryFromLogFile(logFileReader);
		}
		else { 
			LogFileReader logFileReader = new LogFileReader(logFile);
			factory = new HttpUserWorkerFactoryFromLogFile(logFileReader);
		}
		new ReadySteadyThread(threadCount, factory).go();
	}

}

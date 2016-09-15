package com.wolfesoftware.sailfish.http.main;

import java.io.File;

import com.wolfesoftware.sailfish.core.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromLogFile;

public class SailFish {

	public static void main(String[] args) throws 
			BadLogFileException {
		String fileName = args[0];
		int threadCount = Integer.parseInt(args[1]);
		System.out.println(("Running SailFish with file: " + fileName
				+ " thread count: " + threadCount));
		SailFish sailfish = new SailFish();
		File logFile = new File(fileName);
		System.out.println(logFile.getAbsolutePath());
		sailfish.go(logFile, threadCount);
	}

	private void go(File logFile, int threadCount) throws BadLogFileException {
		LogFileReader logFileReader = new LogFileReader(logFile);
		HttpUserWorkerFactoryFromLogFile factory = new HttpUserWorkerFactoryFromLogFile();
		factory.setUrls(logFileReader);
		new ReadySteadyThread(threadCount, factory).go();
	}

}

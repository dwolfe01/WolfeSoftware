package com.wolfesoftware.sailfish.main;

import java.io.File;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.HttpUserWorkerFactoryFromLogFile;
import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;

public class SailFish {

	public static void main(String[] args) throws BadLogFileException,
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
		HttpUserWorkerFactoryFromLogFile factory = createFactory(logFileReader);
		new ReadySteadyThread(threadCount, factory).go();
	}

	private HttpUserWorkerFactoryFromLogFile createFactory(LogFileReader logFileReader)
			throws BadLogFileException {
		HttpUserWorkerFactoryFromLogFile factory = new HttpUserWorkerFactoryFromLogFile();
		factory.setUrls(logFileReader);
		return factory;
	}

}

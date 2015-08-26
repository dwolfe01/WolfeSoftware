package com.wolfesoftware.sailfish.main;

import java.io.File;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.UptimeHttpUserWorkerFactoryFromLogFile;
import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;

public class SailFishUptime {

	public static void main(String[] args) throws BadLogFileException,
			BadLogFileException {
		String fileName = args[0];
		int threadCount = Integer.parseInt(args[1]);
		System.out.println(("Running SailFish with file: " + fileName
				+ " thread count: " + threadCount));
		SailFishUptime sailfish = new SailFishUptime();
		File logFile = new File(fileName);
		System.out.println(logFile.getAbsolutePath());
		sailfish.go(logFile, threadCount);
	}

	private void go(File logFile, int threadCount) throws BadLogFileException {
		LogFileReader logFileReader = new LogFileReader(logFile);
		UptimeHttpUserWorkerFactoryFromLogFile factory = createFactory(logFileReader);
		new ReadySteadyThread(threadCount, factory).go();
	}

	private UptimeHttpUserWorkerFactoryFromLogFile createFactory(LogFileReader logFileReader)
			throws BadLogFileException {
		UptimeHttpUserWorkerFactoryFromLogFile factory = new UptimeHttpUserWorkerFactoryFromLogFile();
		factory.setUrls(logFileReader);
		return factory;
	}

}

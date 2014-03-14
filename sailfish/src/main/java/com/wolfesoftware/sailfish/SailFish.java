package com.wolfesoftware.sailfish;

import java.io.File;
import java.io.IOException;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.HttpSessionWorkerFactory;
import com.wolfesoftware.sailfish.logfilereader.LogFileReader;

public class SailFish {

	public static void main(String[] args) throws IOException {
		String fileName = args[0];
		int threadCount = Integer.parseInt(args[1]);
		System.out.println("Running SailFish with file: " + fileName
				+ " thread count: " + threadCount);
		SailFish sailfish = new SailFish();
		File logFile = new File(fileName);
		System.out.println(logFile.getAbsolutePath());
		sailfish.go(logFile, threadCount);
	}

	private void go(File logFile, int threadCount) throws IOException {
		LogFileReader logFileReader = new LogFileReader(logFile);
		HttpSessionWorkerFactory httpSessionWorkerFactory = new HttpSessionWorkerFactory();
		httpSessionWorkerFactory.setUrls(logFileReader);
		new ReadySteadyThread(threadCount, httpSessionWorkerFactory).go();
	}

}

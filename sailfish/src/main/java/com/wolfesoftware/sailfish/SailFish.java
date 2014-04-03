package com.wolfesoftware.sailfish;

import java.io.File;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.AdvancedHttpSessionWorkerFactory;
import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;

public class SailFish {

	public static void main(String[] args) throws BadLogFileException,
			BadLogFileException {
		Configurator.defaultConfig().formatPattern("{message}").activate();//should be moved to properties file
		String fileName = args[0];
		int threadCount = Integer.parseInt(args[1]);
		Logger.info("Running SailFish with file: " + fileName
				+ " thread count: " + threadCount);
		SailFish sailfish = new SailFish();
		File logFile = new File(fileName);
		Logger.info(logFile.getAbsolutePath());
		sailfish.go(logFile, threadCount);
	}

	private void go(File logFile, int threadCount) throws BadLogFileException {
		LogFileReader logFileReader = new LogFileReader(logFile);
		AdvancedHttpSessionWorkerFactory factory = new AdvancedHttpSessionWorkerFactory();
		factory.setUrls(logFileReader);
		new ReadySteadyThread(threadCount, factory).go();
	}

}

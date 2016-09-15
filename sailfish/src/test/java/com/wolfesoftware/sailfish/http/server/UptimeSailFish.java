package com.wolfesoftware.sailfish.http.server;

import java.io.File;

import com.wolfesoftware.sailfish.core.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.worker.factory.UptimeHttpUserWorkerFactoryFromLogFile;



public class UptimeSailFish {

	public static void main(String[]args) throws Exception {
		JettyServer js = new JettyServer();
		js.go();
		UptimeHttpUserWorkerFactoryFromLogFile factory = new UptimeHttpUserWorkerFactoryFromLogFile();
		LogFileReader logFileReader = new LogFileReader(new File("/Users/dwolfe/development/WolfeSoftware/WolfeSoftware/sailfish/src/test/resources/uptime.txt"));
		factory.setUrls(logFileReader);
		new ReadySteadyThread(10, factory).go();
	}
	
}

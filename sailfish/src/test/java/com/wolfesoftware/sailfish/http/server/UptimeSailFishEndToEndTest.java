package com.wolfesoftware.sailfish.http.server;

import java.io.File;

import com.wolfesoftware.sailfish.core.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.core.concurrency.WorkerFactory;
import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.worker.factory.UptimeHttpUserWorkerFactoryFromLogFile;

public class UptimeSailFishEndToEndTest {

	public static void main(String[]args) throws Exception {
		JettyServer js = new JettyServer();
		js.go();
		LogFileReader logFileReader = new LogFileReader(new File("/Users/dwolfe/development/WolfeSoftware/WolfeSoftware/sailfish/src/test/resources/uptime.txt"));
		WorkerFactory factory = new UptimeHttpUserWorkerFactoryFromLogFile(logFileReader);
		new ReadySteadyThread(10, factory).go();
	}
	
}

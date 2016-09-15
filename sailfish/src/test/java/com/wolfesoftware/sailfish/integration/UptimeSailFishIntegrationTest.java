package com.wolfesoftware.sailfish.integration;

import java.io.File;

import org.eclipse.jetty.embedded.JettyServer;
import org.junit.Test;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.UptimeHttpUserWorkerFactoryFromLogFile;
import com.wolfesoftware.sailfish.logfilereader.LogFileReader;

public class UptimeSailFishIntegrationTest {

	@Test
	public void shouldMakeHttpRequests() throws Exception {
		JettyServer js = new JettyServer();
		js.go();
		UptimeHttpUserWorkerFactoryFromLogFile factory = new UptimeHttpUserWorkerFactoryFromLogFile();
		LogFileReader logFileReader = new LogFileReader(new File("/Users/dwolfe/development/WolfeSoftware/WolfeSoftware/sailfish/src/test/resources/uptime.txt"));
		factory.setUrls(logFileReader);
		new ReadySteadyThread(10, factory).go();
	}
	
}

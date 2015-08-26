package com.wolfesoftware.sailfish.integration;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.UptimeHttpUserWorkerFactoryFromLogFile;
import com.wolfesoftware.sailfish.logfilereader.LogFileReader;

public class UptimeSailFishIntegrationTest {

	@Test
	@Ignore
	public void shouldMakeHttpRequests() throws Exception {
		UptimeHttpUserWorkerFactoryFromLogFile factory = new UptimeHttpUserWorkerFactoryFromLogFile();
		LogFileReader logFileReader = new LogFileReader(new File("/Users/dwolfe/development/WolfeSoftware/WolfeSoftware/sailfish/src/test/resources/uptime.txt"));
		factory.setUrls(logFileReader);
		new ReadySteadyThread(1, factory).go();
	}
	
}

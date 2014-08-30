package com.wolfesoftware.sailfish.integration;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.HttpUserWorkerFactoryFromJSONFile;

public class SailFishIntegrationTest {

	@Test
	public void shouldMakeThreeHttpRequests() throws Exception {
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		String jsonHttpUser = FileUtils
				.readFileToString(ResourceUtils
						.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/simplehttpuser.json"));
		factory.setJSON(jsonHttpUser);
		new ReadySteadyThread(1, factory).go();
	}

}
